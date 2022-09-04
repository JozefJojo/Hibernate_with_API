package com.sda.cz5.entity;

import com.sda.cz5.weatherapi.forecast.ForecastItem;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class EntityModelMapper {
    public static CityForecast getFromModel(ForecastItem item, Location location){
        CityForecast cityForecast = new CityForecast();
        cityForecast.setLocation(location);
        cityForecast.setHumidity(item.getMain().getHumidity());
        cityForecast.setTemp(item.getMain().getTemp());
        cityForecast.setPressure(item.getMain().getPressure());
        cityForecast.setWindSpeed(item.getWind().getSpeed());
        cityForecast.setWindDirection(CityForecast.WindDirection.fromDegerees(item.getWind().getDeg()));
        //2022-09-03 09:00:00
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:MM");
        LocalDateTime dateTime = LocalDateTime.parse(item.getDtTxt(), formatter);
        cityForecast.setDateTime(dateTime);
        return cityForecast;
    }
}
