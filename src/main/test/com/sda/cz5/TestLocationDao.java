package com.sda.cz5;

import com.sda.cz5.dao.LocationDao;
import com.sda.cz5.dao.LocationDaoImpl;
import com.sda.cz5.entity.Location;
import jakarta.persistence.EntityManager;
import org.hibernate.boot.Metadata;
import org.hibernate.boot.MetadataSources;
import org.hibernate.boot.model.naming.ImplicitNamingStrategyJpaCompliantImpl;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
import org.hibernate.service.ServiceRegistry;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.Properties;

public class TestLocationDao {
    static EntityManager entityManager;

    @BeforeAll
    public static void init() {
      entityManager=createEntityManager();
    }

    @Test
    @DisplayName("Testing store location")
    public void testStoreLocation() {
        // given
        LocationDaoImpl locationDao = new LocationDaoImpl(entityManager);
        Location location =  Location.builder()
                .cityName("Praha")
                .latitude(15)
                .longitude(100)
                .build();

        // when
        locationDao.saveLocation(location);

        // then
        Assertions.assertNotNull(location.getId());

    }

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
                .getMetadataBuilder()
                .applyImplicitNamingStrategy(ImplicitNamingStrategyJpaCompliantImpl.INSTANCE)
                .build();
        return metadata.getSessionFactoryBuilder().build().createEntityManager();


    }
}
