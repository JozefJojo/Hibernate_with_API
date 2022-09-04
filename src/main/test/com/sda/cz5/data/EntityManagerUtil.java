package com.sda.cz5.data;

import com.sda.cz5.entity.CityForecast;
import com.sda.cz5.entity.Location;
import jakarta.persistence.EntityManager;
import org.hibernate.boot.Metadata;
import org.hibernate.boot.MetadataSources;
import org.hibernate.boot.model.naming.ImplicitNamingStrategyJpaCompliantImpl;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
import org.hibernate.service.ServiceRegistry;

import java.util.Properties;

public class EntityManagerUtil {
    public static EntityManager createEntityManager() {
        Properties properties = new Properties();
        properties.put("javax.persistence.provider", "org.hibernate.ejb.HibernatePersistence");
        properties.put("javax.persistence.transactionType", "RESOURCE_LOCAL");
        properties.put("hibernate.connection.username", "sa");
        properties.put("hibernate.connection.password" ,"");
        properties.put("hibernate.connection.driver_class","org.h2.Driver");
        properties.put("hibernate.connection.url", String.format("jdbc:h2:mem:%s;MODE=DB2", ""));
        properties.put("hibernate.dialect" ,"org.hibernate.dialect.H2Dialect");
        properties.put("hibernate.hbm2ddl.auto","create-drop");

        ServiceRegistry serviceRegistry = new StandardServiceRegistryBuilder()
                .applySettings(properties)
                .build();

        Metadata metadata = new MetadataSources(serviceRegistry)
                .addAnnotatedClass(Location.class)
                .addAnnotatedClass(CityForecast.class)
                .getMetadataBuilder()
                .applyImplicitNamingStrategy(ImplicitNamingStrategyJpaCompliantImpl.INSTANCE)
                .build();
        return metadata.getSessionFactoryBuilder().build().createEntityManager();


    }
}
