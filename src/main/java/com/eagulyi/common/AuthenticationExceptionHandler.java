package com.eagulyi.common;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.AuthenticationException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import javax.servlet.http.HttpServletResponse;

/**
 * Created by eugene on 6/23/17.
 */
@ControllerAdvice
public class AuthenticationExceptionHandler {
    private static final Logger LOG = LoggerFactory.getLogger(AuthenticationExceptionHandler.class);

    private final ServletUtil servletUtil;

    @Autowired
    public AuthenticationExceptionHandler(ServletUtil servletUtil) {
        this.servletUtil = servletUtil;
    }

    @ExceptionHandler({AuthenticationException.class})
    public void handle(Exception exception, HttpServletResponse response) {
        LOG.error(exception.getMessage());
        servletUtil.writeServletResponse(response, ErrorResponse.of("Cannot authenticate", ErrorCode.AUTHENTICATION, HttpStatus.UNAUTHORIZED));
    }
}
