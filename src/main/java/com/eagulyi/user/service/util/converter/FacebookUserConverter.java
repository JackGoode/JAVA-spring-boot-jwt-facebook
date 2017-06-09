package com.eagulyi.user.service.util.converter;

import com.eagulyi.user.entity.*;
import com.eagulyi.user.model.json.facebook.Concentration;
import com.eagulyi.user.model.json.facebook.FacebookUserData;
import com.eagulyi.user.repository.LocationRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * Created by eugene on 5/26/17.
 */
@Service
public class FacebookUserConverter {

    private final LocationRepository locationRepository;

    @Autowired
    public FacebookUserConverter(LocationRepository locationRepository) {
        this.locationRepository = locationRepository;
    }

    public User convert(FacebookUserData facebookUser) {
        User user = new User();
        user.setDataProvider(DataProvider.FACEBOOK);
        user.setUsername(facebookUser.getEmail());
        user.setFirstName(facebookUser.getFirstName());
        user.setLastName(facebookUser.getLastName());
        user.setCreationDate(LocalDateTime.now());
        user.setFacebookId(facebookUser.getId());
//        user.setLocation(getLocationFromString(facebookUser.getLocation().getName()));
        facebookUser.getWork().forEach(e -> {
            Work work = new Work(e.getEmployer().getName(),
                    e.getPosition().getName(),
                    e.getLocation() != null ? getLocationFromString(e.getLocation().getName()) : null,
                    LocalDate.parse(e.getStartDate(), DateTimeFormatter.ISO_LOCAL_DATE),
                    e.getEndDate() == null ? null : LocalDate.parse(e.getEndDate(), DateTimeFormatter.ISO_LOCAL_DATE));
            user.addWorkItem(work);
        });

        facebookUser.getEducation().forEach(e -> {
            Set<String> specialtiesSet = e.getConcentration().stream().map(Concentration::getName).collect(Collectors.toSet());
            String yearString = e.getYear().getName();

            Education education = new Education(
                    e.getSchool().getName(),
                    e.getType(),
                    yearString.isEmpty() ? null : LocalDate.of(Integer.parseInt(yearString), 1, 1),
                    specialtiesSet);

            user.addEducationItem(education);
        });

        return user;
    }

    private Location getLocationFromString(String locationString) {
        String[] location = locationString.split(", ");

        if (location.length > 1) {
            return locationRepository.findByCity_NameAndCountry_Name(location[0], location[1]).orElseGet(() -> new Location(location[0], location[1]));
        }
        return new Location();
    }
}
