package com.sda.cz5;

import com.sda.cz5.dao.LocationDao;
import com.sda.cz5.dao.LocationFactory;
import com.sda.cz5.entity.Location;
import com.sda.cz5.weatherapi.LocationClient;
import com.sda.cz5.weatherapi.LocationModel;

import java.io.IOException;
import java.util.Arrays;
import java.util.List;
import java.util.Scanner;

public class WeatherMain {

    LocationClient client = new LocationClient();
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
                case "city-down" -> {
                    LocationModel[] locationModels = cityDown(commands[1]);
                    for (LocationModel model : locationModels) {
                        System.out.println(model);
                    }
                }
                case "store-city" -> storeCity(commands);
                case "list-city" -> listCity();
                case "switch-dao" -> switchDao(commands[1]);
                case "help" -> help();
                default -> {
                    System.out.println("Unknown command " + line);
                }
            }
        } catch (Exception e) {
            resolveException(e, line);
        }
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
