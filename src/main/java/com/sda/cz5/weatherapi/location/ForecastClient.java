package com.sda.cz5.weatherapi.location;


import com.sda.cz5.weatherapi.forecast.Forecast;

import java.io.IOException;
import java.util.Optional;

public class ForecastClient extends AbstractLocationClient {

    public Optional<Forecast> getForecast(double lat, double lon) {
        return getForecast(String.valueOf(lat), String.valueOf(lon));
    }

    public Optional<Forecast> getForecast(String lat, String lon) {
        Forecast forest;
        try {
            forest = loadDataFromService(createLocationUrl(lat, lon), Forecast.class);
        } catch (IOException e) {
            return Optional.empty();
        }
        return Optional.of(forest);
    }

    private String createLocationUrl(String lat, String lon) {
        return String.format("https://api.openweathermap.org/data/2.5/forecast?lat=%s&lon=%s&lang=cz&appid=3670cdc2cac8a44b06a02c47179a4159", lat, lon);
    }

}
