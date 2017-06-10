package com.eagulyi.security.endpoint;

import com.eagulyi.facebook.model.token.ConvertTokenException;
import com.eagulyi.facebook.model.token.InvalidTokenException;
import com.eagulyi.security.auth.jwt.extractor.TokenExtractor;
import com.eagulyi.security.config.WebSecurityConfig;
import com.eagulyi.security.model.UserContext;
import com.eagulyi.security.model.token.JwtTokenFactory;
import com.eagulyi.user.entity.User;
import com.eagulyi.user.model.json.facebook.FacebookUserData;
import com.eagulyi.user.service.FacebookService;
import com.eagulyi.user.service.UserServiceImpl;
import com.eagulyi.user.service.util.converter.FacebookUserConverter;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import javax.naming.AuthenticationException;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.transaction.Transactional;
import java.io.IOException;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Created by eugene on 4/14/17.
 */
@RestController
public class FacebookLoginEndpoint {
    private final JwtTokenFactory tokenFactory;
    private final UserServiceImpl userService;
    private final TokenExtractor tokenExtractor;
    private final ObjectMapper objectMapper;
    private final FacebookService facebookService;
    private final FacebookUserConverter userConverter;

    @Autowired
    public FacebookLoginEndpoint(JwtTokenFactory tokenFactory,
                                 UserServiceImpl userService,
                                 @Qualifier("jwtHeaderTokenExtractor") TokenExtractor tokenExtractor,
                                 ObjectMapper objectMapper,
                                 FacebookService facebookService,
                                 FacebookUserConverter userConverter) {
        this.tokenFactory = tokenFactory;
        this.userService = userService;
        this.tokenExtractor = tokenExtractor;
        this.objectMapper = objectMapper;
        this.facebookService = facebookService;
        this.userConverter = userConverter;
    }

    @RequestMapping(value = "/api/auth/facebookLogin", method = RequestMethod.GET, produces = {MediaType.APPLICATION_JSON_VALUE})
    @Transactional
    public @ResponseBody
    void facebookLogin(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException, AuthenticationException, ConvertTokenException, InvalidTokenException {
        String tokenPayload = tokenExtractor.extract(request.getHeader(WebSecurityConfig.FB_TOKEN_HEADER_PARAM));

        String username = facebookService.verifyToken(tokenPayload);

        User user = userService.getByUsername(username).orElseGet(() -> {
            FacebookUserData facebookUser = facebookService.getProfileData(username);
            User u = userConverter.convert(facebookUser);
            return userService.createDefaultUser(u);
        });

        List<GrantedAuthority> authorities = user.getRoles().stream()
                .map(authority -> new SimpleGrantedAuthority(authority.getRole().authority()))
                .collect(Collectors.toList());
        UserContext userContext = UserContext.create(user.getUsername(), authorities);

        response.setStatus(HttpStatus.OK.value());
        response.setContentType(MediaType.APPLICATION_JSON_VALUE);

        objectMapper.writeValue(response.getWriter(), tokenFactory.createTokenPair(userContext));
    }


}