package com.eagulyi.security.exceptions;

import com.eagulyi.security.model.token.JwtToken;
import org.springframework.security.core.AuthenticationException;

public class InvalidTokenException extends AuthenticationException {
    private static final long serialVersionUID = -5959543783324224864L;

    private final JwtToken token;

    public InvalidTokenException(JwtToken token, String msg) {
        super(msg);
        this.token = token;
    }

    public InvalidTokenException(JwtToken token, String msg, Throwable t) {
        super(msg, t);
        this.token = token;
    }

    public String token() {
        return this.token.getToken();
    }
}
