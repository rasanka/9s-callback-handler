package com.ninespokes.app.dto;

public class TokenIntrospectRequestDto {
    private String authorization;
    private String token;
    private String token_type_hint;
    private String introspect_type;
    private String tenantId;

    public String getAuthorization() {
        return authorization;
    }

    public void setAuthorization(String authorization) {
        this.authorization = authorization;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public String getToken_type_hint() {
        return token_type_hint;
    }

    public void setToken_type_hint(String token_type_hint) {
        this.token_type_hint = token_type_hint;
    }

    public String getIntrospect_type() {
        return introspect_type;
    }

    public void setIntrospect_type(String introspect_type) {
        this.introspect_type = introspect_type;
    }

    public String getTenantId() {
        return tenantId;
    }

    public void setTenantId(String tenantId) {
        this.tenantId = tenantId;
    }
}
