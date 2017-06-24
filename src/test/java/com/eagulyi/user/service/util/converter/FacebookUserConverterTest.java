package com.eagulyi.user.service.util.converter;

import com.eagulyi.user.entity.Education;
import com.eagulyi.user.entity.Speciality;
import com.eagulyi.user.entity.User;
import com.eagulyi.user.entity.Work;
import com.eagulyi.user.model.json.facebook.FacebookUserData;
import com.eagulyi.user.repository.UserRepository;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.jsonwebtoken.lang.Assert;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import javax.transaction.Transactional;
import java.io.File;
import java.io.IOException;

/**
 * Created by eugene on 5/29/17.
 */
@SpringBootTest
@RunWith(SpringJUnit4ClassRunner.class)
public class FacebookUserConverterTest {

    @Autowired
    private FacebookUserConverter facebookUserConverter;

    @Autowired
    private UserRepository userRepository;

    private static User user;

    @Before
    @Transactional
    public void init() throws IOException {
        ObjectMapper mapper = new ObjectMapper();
        user = facebookUserConverter.convert(mapper.readValue(new File("src/test/resources/facebookUserData.json"), FacebookUserData.class));
    }

    @After
    public void destroy() {
        userRepository.deleteByUsername("test@gmail.com");
    }

    @Test
    public void test_generalInformationConverted() {
        Assert.isTrue(user.getFirstName().equals("Eugene"));
        Assert.isTrue(user.getLastName().equals("Gulyi"));
        Assert.isTrue(user.getUsername().equals("test@gmail.com"));
        Assert.isTrue(user.getLocation().getCountry().getName().equals("Ukraine"));
        Assert.isTrue(user.getLocation().getCity().getName().equals("Kyiv"));
        Assert.isTrue(user.getEducationItems().size() == 2);
        Assert.isTrue(user.getWorkItems().size() == 3);
    }

    @Test
    public void test_educationConverted() {
        Education[] educations = user.getEducationItems().toArray(new Education[2]);

        Assert.isTrue(educations[0].getSchool().getName().equals("Николаевская гимназия №2"));
        Assert.isTrue(educations[0].getGraduationYear().getYear() == 2006);
        Assert.isTrue(educations[0].getSpecialities().isEmpty());

        Assert.isTrue(educations[1].getSchool().getName().equals("НАУ (авиационный) (бывш. КИИГА, КМУГА)"));
        Assert.isTrue(educations[1].getGraduationYear().getYear() == 2011);
        Speciality[] specialities = educations[1].getSpecialities().toArray(new Speciality[1]);
        Assert.isTrue(specialities[0].getName().equals("Computer Systems and Networks"));
    }

    @Test
    public void test_workConverted() {
        Work[] works = user.getWorkItems().toArray(new Work[3]);

        Assert.isTrue(works[0].getEmployer().getName().equals("Infopulse Ukraine"));
        Assert.isTrue(works[0].getPosition().getName().equals("Operations Engineer"));
        Assert.isTrue(works[0].getLocation().getCountry().getName().equals("Ukraine"));
        Assert.isTrue(works[0].getLocation().getCity().getName().equals("Kyiv"));
        Assert.isTrue(works[0].getStartDate().getYear() == 2008);
        Assert.isTrue(works[0].getEndDate().getYear() == 2010);

        Assert.isTrue(works[1].getEmployer().getName().equals("Infopulse Ukraine"));
        Assert.isTrue(works[1].getPosition().getName().equals("Software Engineer / TL / Tambourine monkey"));
        Assert.isTrue(works[1].getLocation().getCountry().getName().equals("Ukraine"));
        Assert.isTrue(works[1].getLocation().getCity().getName().equals("Kyiv"));
        Assert.isTrue(works[1].getStartDate().getYear() == 2010);
        Assert.isTrue(works[1].getEndDate().getYear() == 2016);

        Assert.isTrue(works[2].getEmployer().getName().equals("Little Pilots"));
        Assert.isTrue(works[2].getPosition().getName().equals("Co-Founder, Software Engineer"));
        Assert.isTrue(works[2].getStartDate().getYear() == 2016);
        Assert.isTrue(works[2].getEndDate() == null);
    }

}