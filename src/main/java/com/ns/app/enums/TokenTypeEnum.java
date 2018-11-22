package com.ns.app.enums;

public enum TokenTypeEnum {

    // @formatter:off
	ACCESS_TOKEN("access_token"), 
	REFRESH_TOKEN("refresh_token"), 
	OPENID("openid");
    // @formatter:on

	private String key;

	private TokenTypeEnum(final String key) {
		this.key = key;
	}

	public String getKey() {
		return this.key;
	}

	public static TokenTypeEnum fromString(String tokenType) {
		for (TokenTypeEnum type : TokenTypeEnum.values()) {
			if (tokenType.equalsIgnoreCase(type.getKey())) {
				return type;
			}
		}
		throw new IllegalArgumentException("token type string doesn't match any of TokenTypeEnum.");
	}
}
