package com.sda.cz5;

import com.sda.cz5.dao.LocationDao;
import com.sda.cz5.dao.LocationFactory;
import com.sda.cz5.entity.Location;
import com.sda.cz5.weatherapi.forecast.Forecast;
import com.sda.cz5.weatherapi.location.ForecastClient;
import com.sda.cz5.weatherapi.location.LocationClient;
import com.sda.cz5.weatherapi.location.LocationModel;

import java.io.IOException;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.Scanner;

public class WeatherMain {

    LocationClient client = new LocationClient();
    ForecastClient forecastClient = new ForecastClient();
    LocationDao locationDao = LocationFactory.createLocationDao(false);

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
                    System.out.print(String.join("\n",chunks));
                }
                case "help" -> help();
                default -> {
                    System.out.println("Unknown command " + line);
                }
            }
        } catch (Exception e) {
            resolveException(e, line);
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
        locationDao.saveLocation(Location.builder().
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
