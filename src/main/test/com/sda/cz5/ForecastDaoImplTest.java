package com.sda.cz5;

import com.sda.cz5.dao.ForecastDaoImpl;
import com.sda.cz5.dao.LocationDaoImpl;
import com.sda.cz5.data.EntityManagerUtil;
import com.sda.cz5.entity.CityForecast;
import com.sda.cz5.entity.Location;
import jakarta.persistence.EntityManager;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;

public class ForecastDaoImplTest {
    private static ForecastDaoImpl forecastsDao;

    @BeforeAll
    public static void init() {
        EntityManager entityManager = EntityManagerUtil.createEntityManager();

        forecastsDao = new ForecastDaoImpl(entityManager);
    }

    @Test
    public void testEmptyCity(){
        CityForecast city=new CityForecast();
        city.setLocation(new Location());
        city.getLocation().setId(2);
        city.setDateTime(LocalDateTime.now());
        boolean aa = forecastsDao.cityForecastExists(city);
        Assertions.assertFalse(aa);
    }

    @Test
    public void testNullCity(){

        boolean aa = forecastsDao.cityForecastExists(null);
        Assertions.assertFalse(aa);
    }

}
