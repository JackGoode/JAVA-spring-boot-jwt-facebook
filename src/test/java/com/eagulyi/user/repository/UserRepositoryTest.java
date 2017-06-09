package com.eagulyi.user.repository;

import com.eagulyi.user.entity.*;
import io.jsonwebtoken.lang.Assert;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import javax.transaction.Transactional;
import java.time.LocalDate;
import java.util.HashSet;
import java.util.Optional;
import java.util.Set;

/**
 * Created by eugene on 5/10/17.
 */
@SpringBootTest
@RunWith(SpringJUnit4ClassRunner.class)
public class UserRepositoryTest {

    @Autowired
    private UserRepository repository;
    private User user;

    @Before
    public void testCanSave() {
        UserBuilder builder = new UserBuilder();
        user = builder.defaultUser().withLocation().withHighSchool().withWork().build();
        repository.save(user);
    }

    @Test
    @Transactional
    public void testCanFindByUsername() {
        Optional<User> savedUser = repository.findByUsername("test");
        Assert.isTrue(savedUser.isPresent());
        Assert.isTrue(savedUser.get().getEducationItems().size() > 0);
        Assert.isTrue(savedUser.get().getWorkItems().size() > 0);
    }

    @After
    public void cleanup() {
        repository.delete(user);
    }


    class UserBuilder {
        private User user;

        private UserBuilder defaultUser() {
            this.user = new User();
            this.user.setUsername("test");
            return this;
        }

        private UserBuilder withLocation() {
            Location location = new Location();
            location.setCity(new City("Kiev"));

            this.user.setLocation(location);
            return this;
        }

        private UserBuilder withHighSchool() {
            Set<Speciality> specialities = new HashSet<>();

            Speciality firstSpeciality = new Speciality();
            firstSpeciality.setName("foreign languages");
            specialities.add(firstSpeciality);

            Speciality secondSpeciality = new Speciality();
            secondSpeciality.setName("economics");
            specialities.add(secondSpeciality);

            School school = new School();
            school.setName("Test high school #1");

            Education education = new Education();
            education.setGraduationYear(LocalDate.of(2011, 1, 1));
            education.setType("high school");
            education.setSpecialities(specialities);
            education.setSchool(school);

            user.addEducationItem(education);
            return this;
        }

        private UserBuilder withWork() {
            Employer employer = new Employer();
            employer.setName("Littlepilots.org");

            Position position = new Position();
            position.setName("Lead Engineer");

            Location location = new Location();
            location.setCity(new City("Prague"));

            Work work = new Work();
            work.setStartDate(LocalDate.of(2016, 5, 3));
            work.setLocation(location);
            work.setEmployer(employer);
            work.setPosition(position);
            user.addWorkItem(work);
            return this;
        }

        User build() {
            return this.user;
        }


    }


}