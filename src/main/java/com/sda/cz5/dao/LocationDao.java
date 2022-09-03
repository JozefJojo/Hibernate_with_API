package com.sda.cz5.dao;

import com.sda.cz5.entity.Location;

import java.util.List;
import java.util.Optional;

public interface LocationDao {
    void saveLocation(Location location);
    List<Location> findAll();

    Optional<Location> findByName(String name);
}
