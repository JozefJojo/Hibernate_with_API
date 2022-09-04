package com.sda.cz5;

import com.sda.cz5.weatherapi.location.LocationModel;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

public class LocationModelTest {
    @Test
    public void testCreateObjectWithDefaultContructor() {
        LocationModel locationModel = new LocationModel();
        Assertions.assertNotNull(locationModel);
    }
}
