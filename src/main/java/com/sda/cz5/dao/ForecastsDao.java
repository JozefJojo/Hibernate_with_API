package com.sda.cz5.dao;

import com.sda.cz5.entity.CityForecast;

public interface ForecastsDao extends Dao<CityForecast>{
    void addCityForecast(CityForecast cityForecast);

    boolean cityForecastExists(CityForecast cityForecast);
}
