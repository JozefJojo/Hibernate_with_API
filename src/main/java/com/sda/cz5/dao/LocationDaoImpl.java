package com.sda.cz5.dao;

import com.sda.cz5.entity.Location;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityTransaction;

import java.util.List;

public class LocationDaoImpl implements LocationDao {

    private EntityManager entityManager ;

    public LocationDaoImpl(EntityManager entityManager) {
        this.entityManager = entityManager;
    }

    @Override
    public void saveLocation(Location location) {
        EntityTransaction transaction = entityManager.getTransaction();
        if(!transaction.isActive()){
            transaction.begin();
        }
        try {
            entityManager.persist(location);
        }catch (Exception e){
            transaction.rollback();
        }
        transaction.commit();

    }

    @Override
    public List<Location> findAll() {
        List<Location> from_location = entityManager.createQuery("FROM Location",Location.class).getResultList();
        return from_location;
    }
}
