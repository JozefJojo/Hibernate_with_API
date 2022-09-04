package com.sda.cz5;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.sda.cz5.data.JsonData;
import com.sda.cz5.weatherapi.forecast.Forecast;
import org.junit.jupiter.api.Test;
import java.io.IOException;


import static org.junit.jupiter.api.Assertions.*;

public class TestDownloadForecast {

    @Test
    public void testParseJson() throws IOException {
        Forecast forecast = JsonData.getForecast();
        assertNotNull(forecast.getForecastItem());
        assertNotNull(forecast.getCity());
        assertNotNull(forecast);

    }


}
