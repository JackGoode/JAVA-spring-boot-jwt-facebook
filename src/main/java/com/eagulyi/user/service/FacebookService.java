package com.eagulyi.user.service;


import com.eagulyi.facebook.model.token.ConvertTokenException;
import com.eagulyi.facebook.model.token.InvalidTokenException;
import com.eagulyi.facebook.service.DataProviderServiceImpl;
import com.eagulyi.facebook.service.TokenVerificationService;
import com.eagulyi.user.model.json.facebook.FacebookUserData;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.naming.AuthenticationException;

/**
 * Created by eugene on 1/20/17.
 */
@Service
public class FacebookService {
    private final TokenVerificationService verificationService;
    private final DataProviderServiceImpl dataProviderService;

    @Autowired
    public FacebookService(TokenVerificationService verificationService, DataProviderServiceImpl dataProviderService) {
        this.verificationService = verificationService;
        this.dataProviderService = dataProviderService;
    }


    public String verifyToken(String facebookAccessToken) throws AuthenticationException, InvalidTokenException, ConvertTokenException {
        return verificationService.verify(facebookAccessToken);
    }

    public FacebookUserData getFbData(String username) {
        return dataProviderService.getForUsername(username);
    }

    public FacebookUserData getProfileData(String username) {
        return dataProviderService.getProfileForUsername(username);
    }
}
