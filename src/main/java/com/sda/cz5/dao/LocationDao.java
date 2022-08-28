package com.sda.cz5.dao;

import com.sda.cz5.entity.Location;
import com.sda.cz5.weatherapi.LocationModel;

import java.util.List;

public interface LocationDao {
    void saveLocation(Location location);
    List<Location> findAll();
}
