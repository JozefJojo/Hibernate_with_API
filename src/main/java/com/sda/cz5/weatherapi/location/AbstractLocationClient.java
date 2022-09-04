package com.sda.cz5.weatherapi.location;

import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.IOException;
import java.net.URI;

/**
 * Class represents client to download JSON object (by ObjectMapper) and pars it to given class (T)
 */
public class AbstractLocationClient{
    private ObjectMapper objectMapper=new ObjectMapper();
    public <T> T loadDataFromService(String url, Class<T> clazz) throws IOException {
        var retrieveObject = objectMapper.readValue(URI.create(url).toURL(), clazz);
        return retrieveObject;
    }

    public void setObjectMapper(ObjectMapper objectMapper) {
        this.objectMapper = objectMapper;
    }
}
