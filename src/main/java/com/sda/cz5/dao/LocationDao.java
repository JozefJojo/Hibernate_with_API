package com.sda.cz5.dao;

import com.sda.cz5.entity.Location;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityTransaction;

import java.util.List;
import java.util.Optional;

public interface LocationDao extends Dao<Location> {

    List<Location> findAll();

    Optional<Location> findByName(String name);


}
