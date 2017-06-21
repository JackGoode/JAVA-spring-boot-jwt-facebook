package com.eagulyi.facebook.model.token;

import org.springframework.security.core.AuthenticationException;

/**
 * Created by eugene on 5/8/17.
 */
public class InvalidTokenException extends AuthenticationException {
    public InvalidTokenException(String message, Throwable t) {
        super(message, t);
    }

    public InvalidTokenException(String s) {
        super(s);
    }
}
