package com.sda.cz5.dao;

import com.sda.cz5.entity.Location;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityTransaction;
import jakarta.persistence.TypedQuery;

import java.util.List;
import java.util.Optional;

public class LocationDaoImpl implements LocationDao{

    private EntityManager entityManager ;

    public LocationDaoImpl(EntityManager entityManager) {
        this.entityManager = entityManager;
    }


    @Override
    public List<Location> findAll() {
        List<Location> from_location = entityManager.createQuery("FROM Location",Location.class).getResultList();
        return from_location;
    }

    @Override
    public Optional<Location> findByName(String name) {
        TypedQuery<Location> query = entityManager.createQuery("FROM Location  WHERE cityName = :name", Location.class);
        query.setParameter("name",name);
        try {
            return Optional.of(query.getSingleResult());
        }catch (Exception e){
            return Optional.empty();
        }

    }

    @Override
    public EntityManager getEntityManager() {
        return entityManager;
    }
}
