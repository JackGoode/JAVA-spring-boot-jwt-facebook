package com.eagulyi.security.auth.facebook;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

/**
 * Created by eugene on 4/22/17.
 */
@SpringBootTest
@RunWith(SpringJUnit4ClassRunner.class)
public class FacebookServiceTest {

    @Value("${environments.prod.com.eagulyi.facebook-service-url}")
    private String facebookServiceUrl;


    @Test
    public void test_profile() {
        System.out.println(facebookServiceUrl);
    }

//
//    @Test
//    public void test_canFetchOther() throws IOException {
//        FacebookUser me = userRepository.findByUsername("eagulyi@gmail.com").get();
//        FacebookUser facebookUserData = dataService.getFbData(me.getUsername());
//
//        Assert.isTrue(!facebookUserData.getFbEducation().isEmpty());
//        Assert.isTrue(!facebookUserData.getWork().isEmpty());
//        Assert.isTrue(!facebookUserData.getUsername().isEmpty());
//        Assert.isTrue(!facebookUserData.getLastNameFirstName().isEmpty());
//        Assert.isTrue(facebookUserData.getLocation() != null);
//    }

}