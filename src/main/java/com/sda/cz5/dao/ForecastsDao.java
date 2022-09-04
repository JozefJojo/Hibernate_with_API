package com.sda.cz5.dao;

import com.sda.cz5.entity.CityForecast;

import java.util.List;

public interface ForecastsDao extends Dao<CityForecast>{
    void addCityForecast(CityForecast cityForecast);

    boolean cityForecastExists(CityForecast cityForecast);

    List<CityForecast> getAllForCityName(String cityName);
}
