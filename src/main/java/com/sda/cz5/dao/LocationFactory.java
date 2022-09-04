package com.sda.cz5.dao;

import jakarta.persistence.EntityManagerFactory;

import static com.sda.cz5.dao.FactoryUtil.createFactory;

public class LocationFactory {
    public static LocationDao createLocationDao(boolean useMemory){
        if(useMemory){
            return new LocationDaoMemory();
        }
        EntityManagerFactory entityManagerFactory=createFactory();
        return new LocationDaoImpl(entityManagerFactory.createEntityManager());
    }

    public static LocationDao createLocationDao(String className) throws ClassNotFoundException, InstantiationException, IllegalAccessException {
        Class<?> aClass = Class.forName(className);
        return (LocationDao) aClass.newInstance();
    }





}
