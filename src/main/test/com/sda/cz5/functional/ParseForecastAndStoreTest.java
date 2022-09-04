package com.sda.cz5.functional;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.sda.cz5.dao.*;
import com.sda.cz5.data.EntityManagerUtil;
import com.sda.cz5.data.JsonData;
import com.sda.cz5.entity.CityForecast;
import com.sda.cz5.entity.EntityModelMapper;
import com.sda.cz5.entity.Location;
import com.sda.cz5.weatherapi.forecast.City;
import com.sda.cz5.weatherapi.forecast.Forecast;
import com.sda.cz5.weatherapi.forecast.ForecastItem;
import jakarta.persistence.EntityManager;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

public class ParseForecastAndStoreTest {

    private static ForecastsDao forecastsDao;
    private static LocationDao locationDao;

    private static EntityManager entityManager;

    @BeforeAll
    public static void init() {
        entityManager = EntityManagerUtil.createEntityManager();
        locationDao =new LocationDaoImpl(entityManager);
        forecastsDao = new ForecastDaoImpl(entityManager);
    }

    @Test
    public void mapModelToEntityAndStoreToDB() throws JsonProcessingException {
        //get example forecast object
        Forecast forecastModel = JsonData.getForecast();
        //create example location according to forecastModel.city
        Location location = createLocation(forecastModel.getCity());
        //ulozime vsechny hodinove predpovedi do db
        for (ForecastItem item : forecastModel.getForecastItem()) {
            //map to entity
            CityForecast cityForecastEntity = EntityModelMapper.getFromModel(item, location);
            //save mapped entity to db if not exists
            if (!forecastsDao.cityForecastExists(cityForecastEntity)) {
                forecastsDao.addCityForecast(cityForecastEntity);
                assertNotNull(cityForecastEntity.getId());
            }
        }
        //clear hibernate cache
        entityManager.clear();

        Optional<Location> orlova = locationDao.findByName(location.getCityName());
        assertTrue(orlova.isPresent());
        assertEquals(40,orlova.get().getForecasts().size());

    }

    private Location createLocation(City city) {
        Location testLocacation = Location.builder()
                .cityName(city.getName())
                .longitude(city.getCoord().getLon())
                .latitude(city.getCoord().getLat())
                .build();
        locationDao.saveObject(testLocacation);
        return testLocacation;
    }
}
