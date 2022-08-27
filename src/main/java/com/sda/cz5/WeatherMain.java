package com.sda.cz5;

import com.sda.cz5.dao.LocationDao;
import com.sda.cz5.entity.Location;
import com.sda.cz5.weatherapi.LocationClient;
import com.sda.cz5.weatherapi.LocationModel;

import java.io.IOException;
import java.util.Scanner;

public class WeatherMain {

    LocationClient client = new LocationClient();
    LocationDao locationDao;

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
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    private void runCommand(String line) throws IOException {
        String[] commands = line.split(" ");
        switch (commands[0]) {
            case "city-down" -> cityDown(commands[1]);
            case "store-city" -> storeCity(commands);
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
