package com.ninespokes.app.dto;

public class TokenRequestDto {
    private String grant_type;
    private String subject_token_type;
    private String subject_token;
    private String context;
    private String username;
    private String password;

    public String getGrant_type() {
        return grant_type;
    }

    public void setGrant_type(String grant_type) {
        this.grant_type = grant_type;
    }

    public String getSubject_token_type() {
        return subject_token_type;
    }

    public void setSubject_token_type(String subject_token_type) {
        this.subject_token_type = subject_token_type;
    }

    public String getSubject_token() {
        return subject_token;
    }

    public void setSubject_token(String subject_token) {
        this.subject_token = subject_token;
    }

    public String getContext() {
        return context;
    }

    public void setContext(String context) {
        this.context = context;
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

}
