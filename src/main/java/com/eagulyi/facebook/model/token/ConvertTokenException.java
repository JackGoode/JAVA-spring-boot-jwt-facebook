package com.eagulyi.facebook.model.token;

import org.springframework.security.core.AuthenticationException;

/**
 * Created by eugene on 5/8/17.
 */
public class ConvertTokenException extends AuthenticationException {
    public ConvertTokenException(String s) {
        super(s);
    }
}
