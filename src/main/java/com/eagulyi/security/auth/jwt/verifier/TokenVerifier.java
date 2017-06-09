package com.eagulyi.security.auth.jwt.verifier;

public interface TokenVerifier {
    boolean verify(String jti);
}
