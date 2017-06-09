package com.eagulyi.user.repository;

import com.eagulyi.user.entity.City;
import com.eagulyi.user.entity.Country;
import com.eagulyi.user.entity.Location;
import io.jsonwebtoken.lang.Assert;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

/**
 * Created by eugene on 5/31/17.
 */

@SpringBootTest
@RunWith(SpringJUnit4ClassRunner.class)
public class LocationRepositoryTest {

    @Autowired
    private LocationRepository repository;
    private Location location;

    @Before
    public void init() {
        location = new Location(new City("Bangalore"), new Country("India"));
        repository.save(location);
    }

    @Test
    public void test_canGetBySettlemtentAndCountryNames() {
        Assert.isTrue(repository.findByCity_NameAndCountry_Name("Bangalore", "India").isPresent());
    }

    @After
    public void cleanup() {
        repository.delete(location);
    }

}