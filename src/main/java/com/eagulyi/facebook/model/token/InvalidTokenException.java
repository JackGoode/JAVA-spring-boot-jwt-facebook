package com.eagulyi.facebook.model.token;

/**
 * Created by eugene on 5/8/17.
 */
public class InvalidTokenException extends Throwable {
    public InvalidTokenException(String message) {
        super(message);
    }
}
