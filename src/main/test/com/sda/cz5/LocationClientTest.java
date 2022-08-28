package com.sda.cz5;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.sda.cz5.entity.Location;
import com.sda.cz5.weatherapi.LocationClient;
import com.sda.cz5.weatherapi.LocationModel;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.io.IOException;
import java.net.URL;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;

public class LocationClientTest {

    @Test
    public void testDownload() throws IOException {
        ObjectMapper objectMapper = Mockito.mock(ObjectMapper.class);

        LocationClient locationClient = new LocationClient();
        locationClient.setObjectMapper(objectMapper);

        LocationModel[] model = new LocationModel[]{
               new LocationModel("Orlova",null,10,20,"US")
        };

        Mockito.when(objectMapper.readValue(any(URL.class), eq(LocationModel[].class))).thenReturn(model);

        LocationModel[] orlova = locationClient.getLocation("Orlova");

        Assertions.assertEquals(model[0].getName(),orlova[0].getName());
        Assertions.assertEquals("US",orlova[0].getCountry());
    }

    @Test
    public void testDownload_loc_ret_empty_when_exception() throws IOException {
        ObjectMapper objectMapper = Mockito.mock(ObjectMapper.class);

        LocationClient locationClient = new LocationClient();
        locationClient.setObjectMapper(objectMapper);

        LocationModel[] model = new LocationModel[]{
                new LocationModel("Orlova",null,10,20,"US")
        };

        Mockito.when(objectMapper.readValue(any(URL.class), eq(LocationModel[].class))).thenThrow(new IOException());

        LocationModel[] orlova = locationClient.getLocation("Orlova");
        Assertions.assertEquals(0,orlova.length);
    }
}
