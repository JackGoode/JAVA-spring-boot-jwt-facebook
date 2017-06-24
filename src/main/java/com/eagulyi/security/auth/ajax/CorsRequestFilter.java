package com.eagulyi.security.auth.ajax;

import com.eagulyi.security.config.JwtSettings;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.security.sasl.AuthenticationException;
import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Component
public class CorsRequestFilter extends OncePerRequestFilter {
    private static final Logger LOG = LoggerFactory.getLogger(CorsRequestFilter.class);
    private static final String ORIGIN = "Origin";

    private final JwtSettings jwtSettings;

    @Autowired
    public CorsRequestFilter(JwtSettings jwtSettings) {
        this.jwtSettings = jwtSettings;
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        String origin = request.getHeader(ORIGIN);
        if (origin!= null) {
            if (origin.equals(jwtSettings.getCorsAllowedOrigin())) {
                response.setHeader("Access-Control-Allow-Origin", jwtSettings.getCorsAllowedOrigin());
                response.setHeader("Access-Control-Allow-Credentials", "true");
                response.setHeader("Access-Control-Allow-Headers",
                        request.getHeader("Access-Control-Request-Headers"));

            } else {
                throw new AuthenticationException("Origin not allowed according to current CORS configuration: " + request.getHeader(ORIGIN) + " Allowed " + jwtSettings.getCorsAllowedOrigin());
            }
        }

        if (request.getMethod().equals("OPTIONS")) {
            try {
                response.getWriter().print("OK");
                response.getWriter().flush();
            } catch (IOException e) {
                LOG.error(e.getMessage());
            }
        } else {
            filterChain.doFilter(request, response);
        }
    }
}
