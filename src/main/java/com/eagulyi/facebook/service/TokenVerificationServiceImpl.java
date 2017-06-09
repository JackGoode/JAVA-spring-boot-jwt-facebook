package com.eagulyi.facebook.service;

import com.eagulyi.facebook.model.entity.UserToken;
import com.eagulyi.facebook.model.token.*;
import com.eagulyi.facebook.model.token.Error;
import com.eagulyi.facebook.repository.UserTokenRepository;
import com.eagulyi.facebook.util.Field;
import com.eagulyi.security.model.token.AccessJwtToken;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import javax.transaction.Transactional;
import java.net.URI;
import java.util.Optional;

/**
 * Created by eugene on 4/14/17.
 */
@Service
public class TokenVerificationServiceImpl implements TokenVerificationService {
    @Value("${com.eagulyi.appId}")
    private String APP_ID;

    @Value("${com.eagulyi.appSecret}")
    private String APP_SECRET;

    @Value("${com.eagulyi.facebook-api-url}")
    private String facebookUrl;

    @Value("${com.eagulyi.facebook-api-version}")
    private String facebookApiVersion;

    private static final String VERIFY_PATH = "debug_token";

    private final UserTokenRepository userTokenRepository;
    private final DataProviderServiceImpl dataProviderService;

    private final RestTemplate template = new RestTemplate();

    @Autowired
    public TokenVerificationServiceImpl(UserTokenRepository userTokenRepository, DataProviderServiceImpl dataProviderService) {
        this.userTokenRepository = userTokenRepository;
        this.dataProviderService = dataProviderService;
    }

    @Override
    @Transactional
    public String verify(String token) throws ConvertTokenException, InvalidTokenException {
        String fbId = getIdFromFBAccessToken(token);
        String longTermToken = convertToLongTermToken(token);
        UserToken userToken = new UserToken(fbId, longTermToken);
        String username = dataProviderService.getForToken(userToken, Field.EMAIL).getEmail();
        // TODO: async
        userToken.setUsername(username);
        userTokenRepository.save(userToken);
        // /async
        return username;
    }

    private String getIdFromFBAccessToken(String token) throws InvalidTokenException {
        String fbId = "";
        Optional<TokenData> tokenDataOptional = Optional.ofNullable(extract(token).getTokenData());
        if (tokenDataOptional.isPresent()) {
            TokenData tokenData = tokenDataOptional.get();
            if (tokenData.getIsValid()) {
                if (tokenData.getUserId() != null) {
                    fbId = tokenData.getUserId();
                }
            } else {
                throw new InvalidTokenException(tokenData.getError().getMessage());
            }
        }
        return fbId;
    }

    private AccessTokenData extract(String token) {
        URI verifyTokenUri = UriComponentsBuilder.newInstance()
                .scheme("https")
                .host(facebookUrl)
                .path(VERIFY_PATH)
                .queryParam("input_token", token)
                .queryParam("access_token", APP_ID + "|" + APP_SECRET)
                .build().toUri();
        ResponseEntity<AccessTokenData> response = template.getForEntity(verifyTokenUri, AccessTokenData.class);
        if (response.getStatusCode().is2xxSuccessful()) {
            return response.getBody();
        } else
            return fallbackAuthenticationResponse(response.getStatusCodeValue() + response.getStatusCode().getReasonPhrase());
    }

    private String convertToLongTermToken(String token) throws ConvertTokenException {
        URI exchangeTokenUri = UriComponentsBuilder.newInstance()
                .scheme("https")
                .host(facebookUrl)
                .path("/oauth/access_token")
                .queryParam("grant_type", "fb_exchange_token")
                .queryParam("client_id", APP_ID)
                .queryParam("client_secret", APP_SECRET)
                .queryParam("fb_exchange_token", token)
                .build().toUri();

        ResponseEntity<AccessJwtToken> response = template.getForEntity(exchangeTokenUri, AccessJwtToken.class);
        if (response.getStatusCode().is2xxSuccessful()) {
            return response.getBody().getToken();
        } else
            throw new ConvertTokenException(response.getStatusCodeValue() + response.getStatusCode().getReasonPhrase());
    }

    private AccessTokenData fallbackAuthenticationResponse(String errorMessage) {
        AccessTokenData response = new AccessTokenData();
        TokenData tokenData = new TokenData();
        tokenData.setIsValid(false);
        Error error = new Error();
        error.setMessage(errorMessage);
        tokenData.setError(error);
        response.setTokenData(tokenData);
        return response;
    }

}
