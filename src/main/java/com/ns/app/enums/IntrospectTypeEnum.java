package com.ns.app.enums;

import java.util.HashMap;
import java.util.Map;

public enum IntrospectTypeEnum {

	AUTHORITATIVE("authoritative"), NON_AUTHORITATIVE("non-authoritative");

	private String key;
	private static Map<String, IntrospectTypeEnum> introspectTypeMap = new HashMap<>();

	static {
		for (IntrospectTypeEnum typeHint : IntrospectTypeEnum.values()) {
			introspectTypeMap.put(typeHint.getKey(), typeHint);
		}
	}

	public static IntrospectTypeEnum getIntrospectType(String introspectType) {
		return introspectTypeMap.get(introspectType);
	}

	private IntrospectTypeEnum(final String key) {
		this.key = key;
	}

	public String getKey() {
		return this.key;
	}
}
