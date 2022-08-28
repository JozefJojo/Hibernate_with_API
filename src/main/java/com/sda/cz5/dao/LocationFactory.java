package com.sda.cz5.dao;

import com.sda.cz5.entity.Location;
import jakarta.persistence.EntityManagerFactory;
import org.hibernate.boot.Metadata;
import org.hibernate.boot.MetadataSources;
import org.hibernate.boot.model.naming.ImplicitNamingStrategyJpaCompliantImpl;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
import org.hibernate.service.ServiceRegistry;

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


    private static EntityManagerFactory createFactory(){
        ServiceRegistry serviceRegistry = new StandardServiceRegistryBuilder()
                .configure("hibernate.cfg.xml")
                .applySetting("hibernate.connection.password",System.getProperty("hibernate-password"))
                .build();

        Metadata metadata = new MetadataSources(serviceRegistry)
                .addAnnotatedClass(Location.class)
                .getMetadataBuilder()
                .applyImplicitNamingStrategy(ImplicitNamingStrategyJpaCompliantImpl.INSTANCE)
                .build();
        return metadata.getSessionFactoryBuilder().build();
    }


}
