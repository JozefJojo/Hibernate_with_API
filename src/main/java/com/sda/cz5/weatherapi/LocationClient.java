package com.sda.cz5.weatherapi;


import com.fasterxml.jackson.databind.ObjectMapper;
import java.io.IOException;
import java.net.URI;

public class LocationClient {

    private ObjectMapper objectMapper=new ObjectMapper();

    public LocationModel[] getLocation(String cityName)  {
        LocationModel[] locationModel ;
        try {
            locationModel = loadDataFromService(createLocationUrl(cityName), LocationModel[].class);
        } catch (IOException e) {
            return new LocationModel[]{};
        }
        return locationModel;
    }
//https://api.openweathermap.org/data/2.5/forecast?lat=49.845943&lon=18.430927&lang=cz&appid=3670cdc2cac8a44b06a02c47179a4159
    private String createLocationUrl(String city) {
        return String.format("https://api.openweathermap.org/geo/1.0/direct?q=%s&limit=10&appid=03dd01195e340291ca0d69409b18f659", city);
    }

    public <T> T loadDataFromService(String url, Class<T> clazz) throws IOException {
        var retrieveObject = objectMapper.readValue(URI.create(url).toURL(), clazz);
        return retrieveObject;
    }


    public void setObjectMapper(ObjectMapper objectMapper) {
        this.objectMapper = objectMapper;
    }
}
