package com.sda.cz5.weatherapi.location;


import com.fasterxml.jackson.databind.ObjectMapper;
import java.io.IOException;
import java.net.URI;

public class LocationClient extends AbstractLocationClient {


    public LocationModel[] getLocation(String cityName)  {
        LocationModel[] locationModel ;
        try {
            locationModel = loadDataFromService(createLocationUrl(cityName), LocationModel[].class);
        } catch (IOException e) {
            return new LocationModel[]{};
        }
        return locationModel;
    }

    private String createLocationUrl(String city) {
        return String.format("https://api.openweathermap.org/geo/1.0/direct?q=%s&limit=10&appid=03dd01195e340291ca0d69409b18f659", city);
    }

}
