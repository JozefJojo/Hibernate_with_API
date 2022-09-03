package com.sda.cz5.dao;

import com.sda.cz5.entity.Location;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

public class LocationDaoMemory implements LocationDao{
    private List<Location> inner=new ArrayList<>();
    @Override
    public void saveLocation(Location location) {
        inner.add(location);
    }

    @Override
    public List<Location> findAll() {
        return Collections.unmodifiableList(inner);
    }

    @Override
    public Optional<Location> findByName(String name) {
        return inner.stream().filter(location -> location.getCityName().equals(name)).findFirst();
    }
}
