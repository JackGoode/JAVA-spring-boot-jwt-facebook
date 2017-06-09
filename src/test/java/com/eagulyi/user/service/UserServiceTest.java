package com.eagulyi.user.service;

import com.eagulyi.security.model.token.FacebookTokenData;
import org.junit.Ignore;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Spy;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

/**
 * Created by eugene on 4/16/17.
 */
@SpringBootTest
@RunWith(SpringJUnit4ClassRunner.class)
public class UserServiceTest {

    @Autowired
    @Spy
    private UserServiceImpl userService;

    @Test
    @Ignore
    //TODO
    public void test_canCreateFromFB() {
        FacebookTokenData userTokenData = new FacebookTokenData();
        userTokenData.setIsValid(true);
        userTokenData.setUserId("11111111");
//        Mockito.when(userService.getFacebookProfile(userTokenData)).thenReturn(new Profile("eugene", "gulyi", "eagulyi@gmail.com", "11111111"));
//        userService.createDefaultUser("", userTokenData);
    }



}