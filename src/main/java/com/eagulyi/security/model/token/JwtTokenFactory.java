package com.eagulyi.security.model.token;

import com.eagulyi.security.config.JwtSettings;
import com.eagulyi.security.model.Scopes;
import com.eagulyi.security.model.UserContext;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.util.*;
import java.util.stream.Collectors;

@Component
public class JwtTokenFactory {
    private final JwtSettings settings;

    @Autowired
    public JwtTokenFactory(JwtSettings settings) {
        this.settings = settings;
    }

    /**
     * Factory method for issuing new JWT Tokens.
     * 
     */
    public AccessJwtToken createAccessJwtToken(UserContext userContext) {
        if (StringUtils.isBlank(userContext.getUsername())) 
            throw new IllegalArgumentException("Cannot create JWT Token without username");

        Claims claims = Jwts.claims().setSubject(userContext.getUsername());
        claims.put("scopes", userContext.getAuthorities().stream().map(Object::toString).collect(Collectors.toList()));

        String token = Jwts.builder()
          .setClaims(claims)
          .setIssuer(settings.getTokenIssuer())
                .setIssuedAt(Date.from(LocalDateTime.now().toInstant(ZoneOffset.UTC)))
                .setExpiration(Date.from(LocalDateTime.now().plusDays(settings.getTokenExpirationTime()).toInstant(ZoneOffset.UTC)))
          .signWith(SignatureAlgorithm.HS512, settings.getTokenSigningKey())
        .compact();

        return new AccessJwtToken(token, claims);
    }

    private JwtToken createRefreshToken(UserContext userContext) {
        if (StringUtils.isBlank(userContext.getUsername())) {
            throw new IllegalArgumentException("Cannot create JWT Token without username");
        }

        Claims claims = Jwts.claims().setSubject(userContext.getUsername());
        claims.put("scopes", Arrays.asList(Scopes.REFRESH_TOKEN.authority()));

        String token = Jwts.builder()
          .setClaims(claims)
          .setIssuer(settings.getTokenIssuer())
          .setId(UUID.randomUUID().toString())
                .setIssuedAt(Date.from(LocalDateTime.now().toInstant(ZoneOffset.UTC)))
                .setExpiration(Date.from(LocalDateTime.now().plusDays(settings.getRefreshTokenExpTime()).toInstant(ZoneOffset.UTC)))
          .signWith(SignatureAlgorithm.HS512, settings.getTokenSigningKey())
        .compact();

        return new AccessJwtToken(token, claims);
    }

    public Map<String, String> createTokenPair(UserContext userContext) {
        JwtToken accessToken = createAccessJwtToken(userContext);
        JwtToken refreshToken = createRefreshToken(userContext);

        Map<String, String> tokenMap = new HashMap<>();
        tokenMap.put("token", accessToken.getToken());
        tokenMap.put("refreshToken", refreshToken.getToken());
        return tokenMap;
    }
}
