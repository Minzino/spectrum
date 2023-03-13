package com.spectrum.domain.user;

public enum Authority {
    GUEST("ROLE_GUEST"),
    USER("ROLE_USER");

    private final String key;

    Authority(String key) {
        this.key = key;
    }

    public String getKey() {
        return key;
    }
}
