package com.ns.app.rest;


import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.HttpClientErrorException;

import com.ns.app.dto.TokenResponseDto;
import com.ns.app.exception.UnauthorizedException;
import com.ns.app.service.AuthService;
import com.ns.app.service.ConnectionService;

@RestController
public class CallbackController {

    private static final Logger LOGGER = LoggerFactory.getLogger(CallbackController.class);

    @Autowired
    private AuthService authService;

    @Autowired
    private ConnectionService connectionService;

    @RequestMapping(path = "/test/callback/{serviceID}", method = RequestMethod.GET, produces = "application/json")
    public void callback(@PathVariable("serviceID") String serviceID, @RequestParam Map<String, String> params) {

        LOGGER.info("Callback handler invoked!");
        try {

            TokenResponseDto loginToken = authService.login();
            TokenResponseDto exchangedToken = authService.exchangeToken(loginToken.getOpenid());

            if (connectionService.callAuthorization(params, serviceID, exchangedToken.getAccess_token())) {
                LOGGER.info("Connection authorization successfull!");
            } else {
                LOGGER.warn("Connection authorization NOT successfull!");
            }

            LOGGER.info("Callback handler completed!");
        } catch (HttpClientErrorException | UnauthorizedException e) {
            LOGGER.error("Error Occured during call back handler -{}", e.getMessage(), e);
        }

    }

}
