package com.ns.app.service;

import java.util.Map;

public interface ConnectionService {

    String getConnectionId(String serviceId, String userId, String accessToken);

    boolean callAuthorization(Map<String, String> params, String serviceId, String accessToken);
}
