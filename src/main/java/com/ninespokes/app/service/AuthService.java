package com.ninespokes.app.service;

import com.ninespokes.app.dto.TokenIntrospectResponseDto;
import com.ninespokes.app.dto.TokenResponseDto;
import com.ninespokes.app.exception.UnauthorizedException;

public interface AuthService {

    TokenResponseDto login() throws UnauthorizedException;

    TokenResponseDto exchangeToken(String token) throws UnauthorizedException;
    
    TokenIntrospectResponseDto introspect(String token) throws UnauthorizedException; 
}
