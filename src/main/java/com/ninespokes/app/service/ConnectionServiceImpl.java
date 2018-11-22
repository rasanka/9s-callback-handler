package com.ninespokes.app.service;

import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;

import com.ninespokes.app.config.AppConfig;
import com.ninespokes.app.dto.ConnectionsResponseWrapper;

@Service
public class ConnectionServiceImpl implements ConnectionService {

    private static final Logger LOGGER = LoggerFactory.getLogger(ConnectionServiceImpl.class);

    @Autowired
    private RestTemplate restTemplate;

    @Autowired
    private AppConfig appConfig;

    @Override
    public String getConnectionId(String serviceId, String userId, String accessToken) {
        LOGGER.info("Calling Get connection endpoint");
        try {
            StringBuilder url = new StringBuilder(appConfig.getConnectionServiceUrl());
            url.append(appConfig.getTenantId()).append("/company/").append(appConfig.getCompanyId()).append("/connections");

            LOGGER.debug("Connection Service Endpoint - {}", url.toString());

            HttpHeaders headers = new HttpHeaders();
            headers.add("Authorization", "Bearer " + accessToken);

            HttpEntity<Object> httpEntity = new HttpEntity<Object>(headers);
            ConnectionsResponseWrapper connectionsResponseWrapper = restTemplate.exchange(url.toString(), HttpMethod.GET, httpEntity,
                ConnectionsResponseWrapper.class).getBody();

            String connectionID = connectionsResponseWrapper.getEmbedded().getConnections().stream().filter(
                (a) -> a.getAppKey().equals(serviceId) && a.getCreatedBy().equals(userId)).findFirst().get().getId();

            LOGGER.info("Retreived Connection ID - {}", connectionID);
            return connectionID;
        } catch (HttpClientErrorException e) {
            LOGGER.error("Error Occured during call back handler -{}", e.getMessage(), e);
        }
        return null;
    }

    @Override
    public boolean callAuthorization(Map<String, String> params, String serviceId, String accessToken) {

        LOGGER.info("Calling Authorization endpoint");
        try {
            StringBuilder url = new StringBuilder(appConfig.getConnectionServiceUrl());
            url.append(appConfig.getTenantId()).append("/company/").append(appConfig.getCompanyId()).append("/connections/").append(
                appConfig.getConnectionId()).append("/authorization");

            LOGGER.debug("Connection Service Endpoint - {}", url.toString());

            HttpHeaders headers = new HttpHeaders();
            headers.setContentType(MediaType.APPLICATION_FORM_URLENCODED);
            headers.add("Authorization", "Bearer " + accessToken);

            MultiValueMap<String, String> map = new LinkedMultiValueMap<String, String>();
            for (String param : params.keySet()) {
                map.add(param, params.get(param));
            }
            map.add("serviceID", serviceId);
            map.add("callback", "true");

            HttpEntity<MultiValueMap<String, String>> request = new HttpEntity<MultiValueMap<String, String>>(map, headers);

            ResponseEntity<String> response = restTemplate.postForEntity(url.toString(), request, String.class);

            return response.getStatusCode().is2xxSuccessful();
        } catch (HttpClientErrorException e) {
            LOGGER.error("Error Occured during call back handler -{}", e.getMessage(), e);
        }
        return false;
    }

    @Bean
    public RestTemplate restTemplate(RestTemplateBuilder builder) {
        return builder.build();
    }

}
