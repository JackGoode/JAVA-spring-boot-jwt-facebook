package com.eagulyi.user.endpoint;

import com.eagulyi.security.auth.jwt.JwtAuthenticationToken;
import com.eagulyi.security.model.UserContext;
import com.eagulyi.user.entity.User;
import com.eagulyi.user.model.json.signup.SignUpForm;
import com.eagulyi.user.service.UserService;
import com.eagulyi.user.service.util.converter.SignUpFormUserConverter;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/profile")
public class ProfileEndpoint {
    private final ObjectMapper objectMapper;
    private final UserService userService;
    private final SignUpFormUserConverter signUpFormUserConverter;

    @Autowired
    public ProfileEndpoint(ObjectMapper objectMapper, UserService userService, SignUpFormUserConverter signUpFormUserConverter) {
        this.objectMapper = objectMapper;
        this.signUpFormUserConverter = signUpFormUserConverter;
        this.objectMapper.configure(DeserializationFeature.ACCEPT_EMPTY_STRING_AS_NULL_OBJECT, true);
        this.userService = userService;
    }

    @RequestMapping(value = "/get", method = RequestMethod.GET)
    public void getUserProfile(JwtAuthenticationToken token, HttpServletResponse response) throws IOException {
        UserContext userContext = (UserContext) token.getPrincipal();
        System.out.println("Requesting user " + userContext.getUsername() + " timestamp: " + LocalDateTime.now());
        User user = userService.getByUsername(userContext.getUsername()).get();

        Map<String, String> responseMap = new HashMap<>();
        responseMap.put("username", user.getUsername());
        responseMap.put("first_name", user.getFirstName());
        responseMap.put("last_name", user.getLastName());
        response.setContentType("application/json;charset=UTF-8");
        objectMapper.writeValue(response.getWriter(), responseMap);
    }

    @RequestMapping(value = "/save", method = RequestMethod.POST)
    public void saveProfile(HttpServletRequest request) throws IOException {
        String userString = request.getReader().lines().collect(Collectors.joining());
        SignUpForm signUpUserData = objectMapper.readValue(userString, SignUpForm.class);
        userService.save(signUpFormUserConverter.convert(signUpUserData));
    }


}