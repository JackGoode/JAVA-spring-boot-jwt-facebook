package com.eagulyi.user.endpoint;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.eagulyi.security.auth.ajax.LoginRequest;
import com.eagulyi.user.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import java.io.IOException;

/**
 * Created by eugene on 4/17/17.
 */
@RestController
public class SignUpEndpoint {
    private final UserService userService;
    private final ObjectMapper objectMapper;

    @Autowired
    public SignUpEndpoint(UserService userService, ObjectMapper objectMapper) {
        this.userService = userService;
        this.objectMapper = objectMapper;
    }

    @RequestMapping(value = "/api/signup", method = RequestMethod.POST)
    public @ResponseBody
    String signUp(HttpServletRequest request) throws IOException {
        LoginRequest loginRequest = objectMapper.readValue(request.getReader(), LoginRequest.class);

        userService.createDefaultUser(loginRequest);
        return "User created";
    }
}
