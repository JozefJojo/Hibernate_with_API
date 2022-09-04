package com.sda.cz5;

import com.sda.cz5.dao.ForecastFactory;
import com.sda.cz5.dao.ForecastsDao;
import com.sda.cz5.dao.LocationDao;
import com.sda.cz5.dao.LocationFactory;
import com.sda.cz5.entity.CityForecast;
import com.sda.cz5.entity.EntityModelMapper;
import com.sda.cz5.entity.Location;
import com.sda.cz5.weatherapi.forecast.Forecast;
import com.sda.cz5.weatherapi.forecast.ForecastItem;
import com.sda.cz5.weatherapi.forecast.ForecastClient;
import com.sda.cz5.weatherapi.location.LocationClient;
import com.sda.cz5.weatherapi.location.LocationModel;

import java.io.IOException;
import java.time.LocalDateTime;
import java.util.*;
import java.util.function.ToDoubleFunction;
import java.util.stream.DoubleStream;
import java.util.stream.Stream;

public class WeatherMain {

    LocationClient client = new LocationClient();
    ForecastClient forecastClient = new ForecastClient();
    LocationDao locationDao = LocationFactory.createLocationDao(false);
    ForecastsDao forecastsDao= ForecastFactory.createForecastDao();

    public static void main(String[] args) {
        String property = System.getProperty("hibernate-password");
        if(property==null){
            System.out.println("specify hibernate-password proberty (by settin VM option. i.e. -Dhibernate-password=password)");
        }
        Scanner scanner = new Scanner(System.in);
        WeatherMain main = new WeatherMain();

        while (true) {
            String line = scanner.nextLine();
            if ("exit".equals(line)) {
                break;
            }
            try {
                main.runCommand(line);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    private void runCommand(String line) throws Exception {
        try {
            String[] commands = line.split(" ");
            switch (commands[0]) {
                case "down-city" -> {
                    LocationModel[] locationModels = cityDown(commands[1]);
                    for (LocationModel model : locationModels) {
                        System.out.println(model);
                    }
                }
                case "store-city" -> storeCity(commands);
                case "list-city" -> listCity();
                case "switch-dao" -> switchDao(commands[1]);
                case "down-forecast" -> {
                    Optional<Forecast> forecast = downForecast(commands);
                    String s = forecast.toString();
                    int chunkSize = 100;

                    String[] chunks = s.split("(?<=\\G.{" + chunkSize + "})");
                    System.out.print(String.join("\n", chunks));
                }
                case "store-forecast" -> storeForecast(commands[1]);
                case "stat" -> printStatistic(commands);
                case "help" -> help();
                default -> {
                    System.out.println("Unknown command " + line);
                }
            }
        } catch (Exception e) {
            resolveException(e, line);
        }
    }

    private void printStatistic(String[] commands) {
        try {
            String city = commands[1];
            String statType = commands[2];//temp|hum|wind
            int historyInHours = Integer.parseInt(commands[3]);
            List<CityForecast> allForCityName = forecastsDao.getAllForCityName(city);
            switch (statType) {
                case "temp" -> printAvgTemp(allForCityName, historyInHours);
                case "hum" -> printAvgHum(allForCityName,historyInHours);
                case "wind" -> printAvgWind(allForCityName,historyInHours);
            }
        } catch (Exception ex) {
            resolveException(ex, String.join(" ", commands));
            ex.printStackTrace();
        }
    }

    private void printAvgWind(List<CityForecast> allForCityName, int futureInHours) {
        printAvgValue(allForCityName,
                futureInHours,
                CityForecast::getWindSpeed,
                "Avg wind speed for next %s hours for city:%s is %.2f ");
    }

    private void printAvgHum(List<CityForecast> allForCityName, int futureInHours) {
        printAvgValue(allForCityName,
                futureInHours,
                CityForecast::getHumidity,
                "Avg humidity for next %s hours for city:%s is %.2f %%");
    }

    private void printAvgTemp(List<CityForecast> allForCityName, int futureInHours) {
        printAvgValue(allForCityName,
                futureInHours,
                CityForecast::getTempCelsius,
                "Avg temperature for next %s hours for city:%s is %.2f celsius");
    }

    private void printAvgValue(List<CityForecast> allForCityName, int futureInHours,
                               ToDoubleFunction<CityForecast> function,String message) {

        LocalDateTime localDateTime = LocalDateTime.now();
        LocalDateTime futureTime = localDateTime.plusHours(futureInHours);


        OptionalDouble average = allForCityName.stream()
                .filter(cityForecast -> cityForecast.getDateTime().isBefore(futureTime))
                .mapToDouble(function)
                .average();

        if(average.isPresent()){
            System.out.println(String.format(
                    message
                    ,futureInHours,"?",average.getAsDouble()));
        }

    }

    private void storeForecast(String city) {
        Optional<Location> cityLocation = locationDao.findByName(city);
        int forecastStoredCount = 0;
        if (cityLocation.isPresent()) {
            Location location = cityLocation.get();
            //stazeni informaci o pocasi z WS( Webove sluzby)
            Optional<Forecast> forecast = forecastClient.getForecast(location.getLatitude(), location.getLongitude());
            if (forecast.isPresent()) {
                List<ForecastItem> forecastItem = forecast.get().getForecastItem();
                //pro kazdou polozku predpovedi ji ulozime do DB jestlize uz v DB neni
                for (ForecastItem item : forecastItem) {
                    //predpoved stazena z WS je ForecastItem (puvodni JSON)
                    //DB pracuje s entitou CityForecast (zjednoduseny objekt, nepotrebujeme vse)
                    //proto prevedeme pomoci metody EntityModelMapper.getFromModel...
                    //na Entitu
                    CityForecast cityForecast = EntityModelMapper.getFromModel(item,location);
                    //ulozime pouze pokud neexistuje predpoved pro dane datum
                    if(!forecastsDao.cityForecastExists(cityForecast)) {
                        forecastsDao.addCityForecast(cityForecast);
                        forecastStoredCount++;
                    }
                }
                System.out.printf("Stored %s forecastes for city: %s\n",forecastStoredCount,location.getCityName());
            } else {
                System.out.println("Cannot read forecast from api");
            }
        } else {
            System.out.printf("City: %s not found. Run store-city first\n");
        }
    }

    private Optional<Forecast> downForecast(String[] commands) {
        //down-forecast Orlova
        //down-forecast lat lon
        if (commands.length == 2) {
            Optional<Location> city = locationDao.findByName(commands[1]);
            if (city.isPresent()) {
                return forecastClient.getForecast(city.get().getLatitude(), city.get().getLongitude());
            } else {
                return Optional.empty();
            }
        }if(commands.length==3){
            return forecastClient.getForecast(commands[1],commands[2]);
        }
        return Optional.empty();
    }

    private void help() {
        System.out.println("city-down cityName - Download and print location of city name");
    }

    private void resolveException(Exception e, String line) {
        System.out.println("Bad command format: " + line);
        System.out.println("Try enter help");
    }

    private void switchDao(String command) throws ClassNotFoundException, InstantiationException, IllegalAccessException {
        if ("m".equals(command)) {
            locationDao = LocationFactory.createLocationDao(true);
        }
        if ("d".equals(command)) {
            locationDao = LocationFactory.createLocationDao(false);
        } else {
            locationDao = LocationFactory.createLocationDao(command);
        }
    }

    private void listCity() {
        List<Location> all = locationDao.findAll();
        for (Location location : all) {
            System.out.println(location);
        }
    }

    private void storeCity(String[] commands) throws IOException {
        String cityName = commands[1];
        double lon = 0;
        double lat = 0;
        if (commands.length == 4) {
            lon = Double.parseDouble(commands[2]);
            lat = Double.parseDouble(commands[3]);
        } else {
            int index = commands.length==3? Integer.parseInt(commands[2]):1;
            LocationModel[] locationModels = cityDown(cityName);
            if(locationModels.length==1||commands.length==3){
                lon = locationModels[index-1].getLon();
                lat = locationModels[index-1].getLat();
            }else {
                System.out.println("Location can not be resolved");
                System.out.println("Possible locations: ");
                printArray(locationModels);
                return;
            }
        }
        locationDao.saveObject(Location.builder().
                latitude(lat).
                longitude(lon).
                cityName(cityName).
                build());

    }

    private void printArray(LocationModel[] locationModels) {
         Arrays.stream(locationModels).forEach(System.out::println);
    }

    private LocationModel[] cityDown(String cityName) throws IOException {
        LocationModel[] location = client.getLocation(cityName);
        return location;
    }
}
