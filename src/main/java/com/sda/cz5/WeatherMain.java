package com.sda.cz5;

import com.sda.cz5.dao.LocationDao;
import com.sda.cz5.dao.LocationFactory;
import com.sda.cz5.entity.Location;
import com.sda.cz5.weatherapi.LocationClient;
import com.sda.cz5.weatherapi.LocationModel;

import java.io.IOException;
import java.util.List;
import java.util.Scanner;

public class WeatherMain {

    LocationClient client = new LocationClient();
    LocationDao locationDao = LocationFactory.createLocationDao(false);

    public static void main(String[] args) {
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
                case "city-down" -> cityDown(commands[1]);
                case "store-city" -> storeCity(commands);
                case "list-city" -> listCity();
                case "switch-dao" -> switchDao(commands[1]);
                case "help" ->help();
                default -> {
                    System.out.println("Unknown command "+line);
                }
            }
        }catch(Exception e){
            resolveException(e,line);
        }
    }

    private void help() {
        System.out.println("city-down cityName - Download and print location of city name");
    }

    private void resolveException(Exception e, String line) {
        System.out.println("Bad command format: "+line);
        System.out.println("Try enter help");
    }

    private void switchDao(String command) throws ClassNotFoundException, InstantiationException, IllegalAccessException {
        if ("m".equals(command)) {
            locationDao = LocationFactory.createLocationDao(true);
        }if("d".equals(command)){
            locationDao = LocationFactory.createLocationDao(false);
        }else{
            locationDao = LocationFactory.createLocationDao(command);
        }
    }

    private void listCity() {
        List<Location> all = locationDao.findAll();
        for (Location location: all) {
            System.out.println(location);
        }
    }

    private void storeCity(String[] commands) {
        String cityName = commands[1];
        double lon = Double.parseDouble(commands[2]);
        double lat = Double.parseDouble(commands[3]);

        locationDao.saveLocation(Location.builder().
                latitude(lat).
                longitude(lon).
                cityName(cityName).
                build());
    }

    private void cityDown(String cityName) throws IOException {
        LocationModel[] location = client.getLocation(cityName);
        for (LocationModel model : location) {
            System.out.println(model);
        }
    }
}
