package com.eagulyi.facebook.service;

import com.eagulyi.facebook.model.entity.UserToken;
import com.eagulyi.facebook.model.token.AccessTokenData;
import com.eagulyi.facebook.model.token.ConvertTokenException;
import com.eagulyi.facebook.model.token.InvalidTokenException;
import com.eagulyi.facebook.model.token.TokenData;
import com.eagulyi.facebook.repository.UserTokenRepository;
import com.eagulyi.facebook.util.Field;
import com.eagulyi.security.model.token.AccessJwtToken;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
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
    private String appId;

    @Value("${com.eagulyi.appSecret}")
    private String appSecret;

    @Value("${com.eagulyi.facebook-api-url}")
    private String facebookUrl;

    @Value("${com.eagulyi.facebook-api-version}")
    private String facebookApiVersion;

    private static final String VERIFY_PATH = "debug_token";

    private final UserTokenRepository userTokenRepository;
    private final FacebookDataServiceImpl dataProviderService;

    private final RestTemplate template = new RestTemplate();

    private static final Logger LOG = LoggerFactory.getLogger(TokenVerificationServiceImpl.class);

    @Autowired
    public TokenVerificationServiceImpl(UserTokenRepository userTokenRepository, FacebookDataServiceImpl dataProviderService) {
        this.userTokenRepository = userTokenRepository;
        this.dataProviderService = dataProviderService;
    }

    @Override
    @Transactional
    public String verify(String token) {
        String fbId = getIdFromFBAccessToken(token);
        String longTermToken = convertToLongTermToken(token);
        UserToken userToken = new UserToken(fbId, longTermToken);
        String username = dataProviderService.getForToken(userToken, Field.EMAIL).getEmail();
        userToken.setUsername(username);
        userTokenRepository.save(userToken);
        return username;
    }

    private String getIdFromFBAccessToken(String token) {
        String fbId = "";
        Optional<TokenData> tokenDataOptional = Optional.ofNullable(verifyAndExtract(token).getTokenData());
        if (tokenDataOptional.isPresent()) {
            TokenData tokenData = tokenDataOptional.get();
            if (tokenData.getIsValid()) {
                if (tokenData.getUserId() != null) {
                    fbId = tokenData.getUserId();
                }
            } else {
                LOG.error("Invalid token");
                throw new InvalidTokenException(tokenData.getError().getMessage());
            }
        }
        return fbId;
    }

    private AccessTokenData verifyAndExtract(String token) {
        URI verifyTokenUri = UriComponentsBuilder.newInstance()
                .scheme("https")
                .host(facebookUrl)
                .path(VERIFY_PATH)
                .queryParam("input_token", token)
                .queryParam("access_token", appId + "|" + appSecret)
                .build().toUri();
        ResponseEntity<AccessTokenData> response = template.getForEntity(verifyTokenUri, AccessTokenData.class);
        if (response.getStatusCode().is2xxSuccessful()) {
            return response.getBody();
        } else throw new InvalidTokenException(response.getStatusCode().getReasonPhrase());
    }

    private String convertToLongTermToken(String token) {
        URI exchangeTokenUri = UriComponentsBuilder.newInstance()
                .scheme("https")
                .host(facebookUrl)
                .path("/oauth/access_token")
                .queryParam("grant_type", "fb_exchange_token")
                .queryParam("client_id", appId)
                .queryParam("client_secret", appSecret)
                .queryParam("fb_exchange_token", token)
                .build().toUri();

        ResponseEntity<AccessJwtToken> response = template.getForEntity(exchangeTokenUri, AccessJwtToken.class);
        if (response.getStatusCode().is2xxSuccessful()) {
            return response.getBody().getToken();
        } else {
            LOG.error("Error while converting facebook access token ");
            throw new ConvertTokenException(response.getStatusCodeValue() + response.getStatusCode().getReasonPhrase());
        }
    }

}
