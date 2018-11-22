package com.ninespokes.app.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@ConfigurationProperties
@Component
public class AppConfig {

    @Value("${connection.service.url}")
    private String connectionServiceUrl;
    
    @Value("${auth.service.url}")
    private String authServiceUrl;

    @Value("${tenant.id}")
    private String tenantId;

    @Value("${company.id}")
    private String companyId;

    @Value("${connection.id}")
    private String connectionId;

    @Value("${username}")
    private String username;
    
    @Value("${password}")
    private String password;
    
    @Value("${authorization.clientid}")
    private String authClientId;
    
    @Value("${authorization.credentials}")
    private String authCredentials;

    public String getConnectionServiceUrl() {
        return connectionServiceUrl;
    }

    public void setConnectionServiceUrl(String connectionServiceUrl) {
        this.connectionServiceUrl = connectionServiceUrl;
    }

    public String getAuthServiceUrl() {
        return authServiceUrl;
    }

    public void setAuthServiceUrl(String authServiceUrl) {
        this.authServiceUrl = authServiceUrl;
    }

    public String getTenantId() {
        return tenantId;
    }

    public void setTenantId(String tenantId) {
        this.tenantId = tenantId;
    }

    public String getCompanyId() {
        return companyId;
    }

    public void setCompanyId(String companyId) {
        this.companyId = companyId;
    }

    public String getConnectionId() {
        return connectionId;
    }

    public void setConnectionId(String connectionId) {
        this.connectionId = connectionId;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getAuthClientId() {
        return authClientId;
    }

    public void setAuthClientId(String authClientId) {
        this.authClientId = authClientId;
    }

    public String getAuthCredentials() {
        return authCredentials;
    }

    public void setAuthCredentials(String authCredentials) {
        this.authCredentials = authCredentials;
    }
    
}

