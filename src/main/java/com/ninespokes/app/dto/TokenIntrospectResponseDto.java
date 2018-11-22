package com.ninespokes.app.dto;

public class TokenIntrospectResponseDto {
    private String jti;
    private String iss;
    private String aud;
    private String sub;
    private String iat;
    private String exp;
    private String token_type;
    private String username;
    private String client_id;
    private String [] scope;
    private boolean active;
    private TokenClaim Claims;

    public String getJti() {
        return jti;
    }

    public void setJti(String jti) {
        this.jti = jti;
    }

    public String getIss() {
        return iss;
    }

    public void setIss(String iss) {
        this.iss = iss;
    }

    public String getAud() {
        return aud;
    }

    public void setAud(String aud) {
        this.aud = aud;
    }

    public String getSub() {
        return sub;
    }

    public void setSub(String sub) {
        this.sub = sub;
    }

    public String getIat() {
        return iat;
    }

    public void setIat(String iat) {
        this.iat = iat;
    }

    public String getExp() {
        return exp;
    }

    public void setExp(String exp) {
        this.exp = exp;
    }

    public String getToken_type() {
        return token_type;
    }

    public void setToken_type(String token_type) {
        this.token_type = token_type;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getClient_id() {
        return client_id;
    }

    public void setClient_id(String client_id) {
        this.client_id = client_id;
    }

    public String [] getScope() {
        return scope;
    }

    public void setScope(String scope) {
        if (scope != null){
            this.scope = scope.split(" ");
        }
    }

    public boolean getActive() {
        return active;
    }

    public void setActive(boolean active) {
        this.active = active;
    }

    public TokenClaim getClaims() {
        return Claims;
    }

    public void setClaims(TokenClaim claims) {
        Claims = claims;
    }
}
