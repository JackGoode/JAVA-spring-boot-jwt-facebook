package com.eagulyi.user.repository;

import com.eagulyi.user.entity.Location;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import java.util.Optional;

/**
 * Created by eugene on 5/31/17.
 */
public interface LocationRepository extends CrudRepository<Location, Long> {

    Optional<Location> findByCity_NameAndCountry_Name(@Param("cityName") String cityName, @Param("countryName") String countryName);

}
