package com.eagulyi.user.service.util.converter;

import com.eagulyi.user.entity.*;
import com.eagulyi.user.model.json.signup.SignUpForm;
import com.eagulyi.user.repository.LocationRepository;
import com.eagulyi.user.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalDateTime;

/**
 * Created by eugene on 5/26/17.
 */
@Service
public class SignUpFormUserConverter implements UserConverter<SignUpForm> {

    private final LocationRepository locationRepository;
    private final UserService userService;

    @Autowired
    public SignUpFormUserConverter(LocationRepository locationRepository, UserService userService) {
        this.locationRepository = locationRepository;
        this.userService = userService;
    }

    public User convert(SignUpForm signUpUserData) {
        User user = getByUsername(signUpUserData);

        user.setDataProvider(DataProvider.INTERNAL);
        user.setUsername(signUpUserData.getEmail());
        user.setFirstName(signUpUserData.getFirstName());
        user.setLastName(signUpUserData.getLastName());
        user.setCreationDate(LocalDateTime.now());
        user.setLocation(
                locationRepository.findByCity_NameAndCountry_Name(signUpUserData.getCity(), signUpUserData.getCountry())
                        .orElseGet(() -> new Location(signUpUserData.getCity(), signUpUserData.getCountry())));


        signUpUserData.getWorkList().forEach(e -> {
            Work work = new Work(e.getCompany(),
                    e.getPosition(),
                    null,
                    LocalDate.of(Integer.parseInt(e.getStartDate()), 1, 1),
                    e.getEndDate().isEmpty() ? null : LocalDate.of(Integer.parseInt(e.getEndDate()), 1, 1));
            user.addWorkItem(work);
        });

        signUpUserData.getEducationList().forEach(e -> {
            String yearString = e.getYear();

            Education education = new Education(
                    e.getInstitution(),
                    yearString.isEmpty() ? null : LocalDate.of(Integer.parseInt(yearString), 1, 1),
                    e.getSpeciality());

            user.addEducationItem(education);
        });

        return user;
    }

    User getByUsername(SignUpForm signUpUserData) {
        return userService.getByUsername(signUpUserData.getEmail());
    }

}
