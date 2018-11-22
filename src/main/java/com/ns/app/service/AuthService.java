package com.ns.app.service;

import com.ns.app.dto.TokenIntrospectResponseDto;
import com.ns.app.dto.TokenResponseDto;
import com.ns.app.exception.UnauthorizedException;

public interface AuthService {

    TokenResponseDto login() throws UnauthorizedException;

    TokenResponseDto exchangeToken(String token) throws UnauthorizedException;
    
    TokenIntrospectResponseDto introspect(String token) throws UnauthorizedException; 
}
