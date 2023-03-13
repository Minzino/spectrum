package com.spectrum.auth.provider;

import java.util.Map;

public class InMemoryProviderRepository {
    private final Map<String, OAuthProvider> providers;

    public InMemoryProviderRepository(Map<String, OAuthProvider> providers) {
        this.providers = providers;
    }

    public OAuthProvider findByProviderName(String name) {
        return providers.get(name);
    }
}
