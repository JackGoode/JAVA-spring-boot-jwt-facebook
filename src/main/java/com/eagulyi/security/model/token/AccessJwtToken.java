package com.eagulyi.security.model.token;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import io.jsonwebtoken.Claims;

public final class AccessJwtToken implements JwtToken {

    private final String rawToken;
    @JsonIgnore private Claims claims;

    public AccessJwtToken(@JsonProperty("access_token") String rawToken) {
        this.rawToken = rawToken;
    }

    AccessJwtToken(final String token, Claims claims) {
        this.rawToken = token;
        this.claims = claims;
    }

    public String getToken() {
        return this.rawToken;
    }

    public Claims getClaims() {
        return claims;
    }
}
