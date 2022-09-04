package com.sda.cz5.dao;

import com.sda.cz5.entity.CityForecast;
import com.sda.cz5.entity.Location;
import jakarta.persistence.EntityManagerFactory;
import org.h2.mvstore.db.RowDataType;
import org.hibernate.boot.Metadata;
import org.hibernate.boot.MetadataSources;
import org.hibernate.boot.model.naming.ImplicitNamingStrategyJpaCompliantImpl;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
import org.hibernate.service.ServiceRegistry;

import static com.sda.cz5.dao.FactoryUtil.createFactory;

public class ForecastFactory {

    public static ForecastsDao createForecastDao(){

        EntityManagerFactory entityManagerFactory=createFactory();
        return new ForecastDaoImpl(entityManagerFactory.createEntityManager());
    }


}
