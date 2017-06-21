package com.eagulyi.user.service;


import com.eagulyi.facebook.service.FacebookDataServiceImpl;
import com.eagulyi.facebook.service.TokenVerificationService;
import com.eagulyi.security.auth.jwt.extractor.TokenExtractor;
import com.eagulyi.security.model.UserContext;
import com.eagulyi.user.entity.User;
import com.eagulyi.user.model.json.facebook.FacebookUserData;
import com.eagulyi.user.service.util.converter.FacebookUserConverter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

/**
 * Created by eugene on 1/20/17.
 */
@Service
public class FacebookService {
    private final TokenVerificationService verificationService;
    private final FacebookDataServiceImpl facebookDataService;
    private final UserService userService;
    private final FacebookUserConverter facebookUserConverter;
    private final TokenExtractor tokenExtractor;

    private static final Logger LOG = LoggerFactory.getLogger(FacebookService.class);

    @Autowired
    public FacebookService(TokenVerificationService verificationService,
                           FacebookDataServiceImpl facebookDataService,
                           UserService userService,
                           FacebookUserConverter facebookUserConverter,
                           @Qualifier("jwtHeaderTokenExtractor") TokenExtractor tokenExtractor) {
        this.verificationService = verificationService;
        this.facebookDataService = facebookDataService;
        this.userService = userService;
        this.facebookUserConverter = facebookUserConverter;
        this.tokenExtractor = tokenExtractor;
    }


    public String verifyToken(String facebookAccessToken) throws AuthenticationException {
        return verificationService.verify(facebookAccessToken);
    }

    public FacebookUserData getFbData(String username) {
        return facebookDataService.getForUsername(username);
    }

    public FacebookUserData getProfileData(String username) {
        return facebookDataService.getProfileForUsername(username);
    }

    /**
     * Processes facebook authentication token: <br>
     * - verifies token <br>
     * - converts to long-term <br>
     * - creates default user account or updates existing one with FB reference <br>
     *
     * @param token facebook access token
     * @return UserContext - object representing user
     */
    public UserContext processFbToken(String token) throws AuthenticationException {
        String tokenPayload = tokenExtractor.extract(token);
        String username;
        String errorMessage = "";
        username = verifyToken(tokenPayload);

        User user = userService.getByUsername(username).orElseGet(() -> {
            FacebookUserData facebookUser = getProfileData(username);
            User u = facebookUserConverter.convert(facebookUser);
            return userService.createDefaultUser(u);
        });

        List<GrantedAuthority> authorities = user.getRoles().stream()
                .map(authority -> new SimpleGrantedAuthority(authority.getRole().authority()))
                .collect(Collectors.toList());
        return UserContext.create(user.getUsername(), authorities);
    }
}
