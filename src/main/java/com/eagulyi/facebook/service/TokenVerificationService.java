package com.eagulyi.facebook.service;


import com.eagulyi.facebook.model.token.ConvertTokenException;
import com.eagulyi.facebook.model.token.InvalidTokenException;

/**
 * Created by eugene on 6/9/17.
 */
public interface TokenVerificationService {
    String verify(String token) throws ConvertTokenException, InvalidTokenException;
}
