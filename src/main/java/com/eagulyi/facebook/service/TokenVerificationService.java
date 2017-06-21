package com.eagulyi.facebook.service;


import org.springframework.security.core.AuthenticationException;

/**
 * Created by eugene on 6/9/17.
 */
public interface TokenVerificationService {
    String verify(String token) throws AuthenticationException;
}
