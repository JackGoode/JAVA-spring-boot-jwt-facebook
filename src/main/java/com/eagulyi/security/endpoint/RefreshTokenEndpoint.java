package com.eagulyi.security.endpoint;

import com.eagulyi.facebook.model.token.InvalidTokenException;
import com.eagulyi.security.auth.jwt.JwtAuthenticationToken;
import com.eagulyi.security.auth.jwt.extractor.TokenExtractor;
import com.eagulyi.security.config.JwtSettings;
import com.eagulyi.security.config.WebSecurityConfig;
import com.eagulyi.security.model.UserContext;
import com.eagulyi.security.model.token.JwtToken;
import com.eagulyi.security.model.token.JwtTokenFactory;
import com.eagulyi.security.model.token.RawAccessJwtToken;
import com.eagulyi.security.model.token.RefreshToken;
import com.eagulyi.user.entity.User;
import com.eagulyi.user.service.UserServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.MediaType;
import org.springframework.security.authentication.InsufficientAuthenticationException;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;
import java.util.stream.Collectors;

@RestController
public class RefreshTokenEndpoint {
    private final JwtTokenFactory tokenFactory;
    private final JwtSettings jwtSettings;
    private final UserServiceImpl userService;
    private final TokenExtractor tokenExtractor;

    @Autowired
    public RefreshTokenEndpoint(JwtTokenFactory tokenFactory, JwtSettings jwtSettings, UserServiceImpl userService,
                                @Qualifier("jwtHeaderTokenExtractor") TokenExtractor tokenExtractor) {
        this.tokenFactory = tokenFactory;
        this.jwtSettings = jwtSettings;
        this.userService = userService;
        this.tokenExtractor = tokenExtractor;
    }

    @RequestMapping(value = "/api/auth/token", method = RequestMethod.GET, produces = {MediaType.APPLICATION_JSON_VALUE})
    public @ResponseBody
    JwtToken refreshToken(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
        String tokenPayload = tokenExtractor.extract(request.getHeader(WebSecurityConfig.JWT_TOKEN_HEADER_PARAM));

        RawAccessJwtToken rawToken = new RawAccessJwtToken(tokenPayload);
        RefreshToken refreshToken = RefreshToken.create(rawToken, jwtSettings.getTokenSigningKey()).orElseThrow(() -> new InvalidTokenException("Invalid token"));

        String subject = refreshToken.getSubject();
        User user = userService.getByUsername(subject).orElseThrow(() -> new UsernameNotFoundException("User not found: " + subject));

        if (user.getRoles() == null) throw new InsufficientAuthenticationException("User has no roles assigned");
        List<GrantedAuthority> authorities = user.getRoles().stream()
                .map(authority -> new SimpleGrantedAuthority(authority.getRole().authority()))
                .collect(Collectors.toList());

        UserContext userContext = UserContext.create(user.getUsername(), authorities);

        return tokenFactory.createAccessJwtToken(userContext);
    }

    @RequestMapping(value = "/api/me", method = RequestMethod.GET)
    public @ResponseBody
    UserContext get(JwtAuthenticationToken token) {
        return (UserContext) token.getPrincipal();
    }

}
