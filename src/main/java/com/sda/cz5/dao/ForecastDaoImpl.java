package com.sda.cz5.dao;

import com.sda.cz5.entity.CityForecast;
import jakarta.persistence.EntityManager;
import jakarta.persistence.Query;
import jakarta.persistence.TypedQuery;

public class ForecastDaoImpl implements ForecastsDao {

    private EntityManager entityManager;

    public ForecastDaoImpl(EntityManager entityManager) {
        this.entityManager = entityManager;
    }

    @Override
    public void addCityForecast(CityForecast cityForecast) {
        saveObject(cityForecast);
    }

    @Override
    public boolean cityForecastExists(CityForecast cityForecast) {
        TypedQuery<Integer> query = entityManager.createQuery("SELECT count(id) FROM CityForecast " +
                "WHERE location.id=:locId AND dateTime=:dt", Integer.class);
        query.setParameter("locId", cityForecast.getLocation().getId());
        query.setParameter("dt", cityForecast.getDateTime());
        return query.getSingleResult() == 1;

    }

    @Override
    public EntityManager getEntityManager() {
        return entityManager;
    }
}
