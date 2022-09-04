package com.sda.cz5;

import com.sda.cz5.dao.LocationDaoImpl;
import com.sda.cz5.data.EntityManagerUtil;
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
      entityManager= EntityManagerUtil.createEntityManager();
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
        locationDao.saveObject(location);

        // then
        Assertions.assertNotNull(location.getId());

    }


}
