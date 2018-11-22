package com.ninespokes.app.service;

import java.util.Base64;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;

import com.ninespokes.app.config.AppConfig;
import com.ninespokes.app.dto.TokenIntrospectRequestDto;
import com.ninespokes.app.dto.TokenIntrospectResponseDto;
import com.ninespokes.app.dto.TokenRequestDto;
import com.ninespokes.app.dto.TokenResponseDto;
import com.ninespokes.app.enums.IntrospectTypeEnum;
import com.ninespokes.app.enums.TokenTypeEnum;
import com.ninespokes.app.exception.UnauthorizedException;

@Service
public class AuthServiceImpl implements AuthService {

    private final Logger LOGGER = LoggerFactory.getLogger(AuthServiceImpl.class);

    private RestTemplate restTemplate;
    private AppConfig appConfig;

    @Autowired
    public AuthServiceImpl(RestTemplateBuilder restTemplateBuilder, AppConfig appConfig) {
        this.restTemplate = restTemplateBuilder.build();
        this.appConfig = appConfig;
    }


    @Override
    public TokenResponseDto login() throws UnauthorizedException {

        LOGGER.info("Calling auth service - login");
        TokenRequestDto loginRequest = new TokenRequestDto();
        loginRequest.setGrant_type("password");
        loginRequest.setUsername(appConfig.getUsername());
        loginRequest.setPassword(appConfig.getPassword());

        String exchangeTokenUrl = appConfig.getAuthServiceUrl() + appConfig.getTenantId() + "/token";
        LOGGER.debug("Auth Service URL: {}", exchangeTokenUrl);

        HttpHeaders requestHeaders = new HttpHeaders();
        requestHeaders.add("Authorization", "Basic MTIzNDU2OnNlY3JldA==");
        requestHeaders.setContentType(MediaType.APPLICATION_FORM_URLENCODED);

        MultiValueMap<String, String> requestMap = new LinkedMultiValueMap<>();
        requestMap.add("grant_type", loginRequest.getGrant_type());
        requestMap.add("username", loginRequest.getUsername());
        requestMap.add("password", loginRequest.getPassword());

        HttpEntity<MultiValueMap<String, String>> requestEntity = new HttpEntity<>(requestMap, requestHeaders);
        ResponseEntity<TokenResponseDto> loginResponse = restTemplate.postForEntity(exchangeTokenUrl, requestEntity,
            TokenResponseDto.class);

        if (loginResponse == null) {
            LOGGER.error("Authorization error, null response from auth service!");
            throw new UnauthorizedException("Authorization error, null response from auth service!");
        }

        if (!HttpStatus.OK.equals(loginResponse.getStatusCode())) {
            int interospectStatusCode = (loginResponse.getStatusCode() != null ? loginResponse.getStatusCode().value() : -1);
            LOGGER.error("Authorization error, Token not accepted by auth-service, error code: " + interospectStatusCode);
            throw new UnauthorizedException("Token not accepted by auth-service, error code: " + interospectStatusCode);
        }

        return loginResponse.getBody();
    }

    @Override
    public TokenResponseDto exchangeToken(String token) throws UnauthorizedException {

        LOGGER.info("Calling auth service - exchange token");
        TokenRequestDto exchangeTokenRequest = new TokenRequestDto();
        exchangeTokenRequest.setGrant_type("token-exchange");
        exchangeTokenRequest.setSubject_token_type("openid");
        exchangeTokenRequest.setSubject_token(token);
        exchangeTokenRequest.setContext(appConfig.getCompanyId());

        String exchangeTokenUrl = appConfig.getAuthServiceUrl() + appConfig.getTenantId() + "/token";
        LOGGER.debug("Auth Service URL: {}", exchangeTokenUrl);

        HttpHeaders requestHeaders = new HttpHeaders();
        requestHeaders.add("Authorization", "Basic MTIzNDU2OnNlY3JldA==");
        requestHeaders.setContentType(MediaType.APPLICATION_FORM_URLENCODED);

        MultiValueMap<String, String> requestMap = new LinkedMultiValueMap<>();
        requestMap.add("grant_type", exchangeTokenRequest.getGrant_type());
        requestMap.add("subject_token_type", exchangeTokenRequest.getSubject_token_type());
        requestMap.add("subject_token", exchangeTokenRequest.getSubject_token());
        requestMap.add("context", exchangeTokenRequest.getContext());

        HttpEntity<MultiValueMap<String, String>> requestEntity = new HttpEntity<>(requestMap, requestHeaders);
        ResponseEntity<TokenResponseDto> exchangeTokenResponse = restTemplate.postForEntity(exchangeTokenUrl, requestEntity,
            TokenResponseDto.class);

        if (exchangeTokenResponse == null) {
            LOGGER.error("Authorization error, null response from auth service!");
            throw new UnauthorizedException("Authorization error, null response from auth service!");
        }

        if (!HttpStatus.OK.equals(exchangeTokenResponse.getStatusCode())) {
            int interospectStatusCode = (exchangeTokenResponse.getStatusCode() != null ? exchangeTokenResponse.getStatusCode().value()
                    : -1);
            LOGGER.error("Authorization error, Token not accepted by auth-service, error code: " + interospectStatusCode);
            throw new UnauthorizedException("Token not accepted by auth-service, error code: " + interospectStatusCode);
        }

        return exchangeTokenResponse.getBody();
    }
    
    @Override
    public TokenIntrospectResponseDto introspect(String token) throws UnauthorizedException {
        
        LOGGER.info("Calling auth service - introspect");
        TokenIntrospectRequestDto tokenIntrospectRequestDto = new TokenIntrospectRequestDto();
        tokenIntrospectRequestDto.setAuthorization(String.format("%1$s:%2$s", appConfig.getAuthClientId(),
            appConfig.getAuthCredentials()));
        tokenIntrospectRequestDto.setToken(token);
        tokenIntrospectRequestDto.setToken_type_hint(TokenTypeEnum.ACCESS_TOKEN.getKey());
        tokenIntrospectRequestDto.setIntrospect_type(IntrospectTypeEnum.AUTHORITATIVE.getKey());
        tokenIntrospectRequestDto.setTenantId(appConfig.getTenantId());

        String authServiceIntrospectURL = appConfig.getAuthServiceUrl() + appConfig.getTenantId() + "/introspect";
        LOGGER.debug("Auth Service URL: {}", authServiceIntrospectURL);

        HttpHeaders requestHeaders = new HttpHeaders();
        requestHeaders.add("Authorization", "Basic " + Base64.getEncoder()
                .encodeToString(tokenIntrospectRequestDto.getAuthorization().getBytes()));
        requestHeaders.add("X-Tenant-Id", tokenIntrospectRequestDto.getTenantId());
        requestHeaders.setContentType(MediaType.APPLICATION_FORM_URLENCODED);

        MultiValueMap<String, String> requestMap = new LinkedMultiValueMap<>();
        requestMap.add("token", tokenIntrospectRequestDto.getToken());
        requestMap.add("token_type_hint", tokenIntrospectRequestDto.getToken_type_hint());
        requestMap.add("introspect_type", tokenIntrospectRequestDto.getIntrospect_type());

        HttpEntity<MultiValueMap<String, String>> requestEntity = new HttpEntity<>(requestMap, requestHeaders);
        ResponseEntity<TokenIntrospectResponseDto> introspectResponse = restTemplate.postForEntity(
            authServiceIntrospectURL, requestEntity, TokenIntrospectResponseDto.class);

        if (introspectResponse == null){
            LOGGER.error("Authorization error, null response from auth service!");
            throw new UnauthorizedException("Authorization error, null response from auth service!");
        }

        if (!HttpStatus.OK.equals(introspectResponse.getStatusCode())) {
            int interospectStatusCode = (introspectResponse.getStatusCode() != null ? introspectResponse.getStatusCode().value() : -1);
            LOGGER.error("Authorization error, Token not accepted by auth-service, error code: " + interospectStatusCode);
            throw new UnauthorizedException("Token not accepted by auth-service, error code: " + interospectStatusCode);
        }

        return introspectResponse.getBody();
    }

}
