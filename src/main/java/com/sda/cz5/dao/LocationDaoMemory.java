package com.sda.cz5.dao;

import com.sda.cz5.entity.Location;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

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
}
