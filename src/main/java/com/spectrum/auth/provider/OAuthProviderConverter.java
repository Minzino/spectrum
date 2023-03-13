package com.spectrum.auth.provider;

import com.spectrum.auth.OAuthProperties;
import java.util.HashMap;
import java.util.Map;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class OAuthProviderConverter {

    public static Map<String, OAuthProvider> convertToOAuthProviders(OAuthProperties properties) {
        Map<String, OAuthProvider> oAuthProviders = new HashMap<>();

        properties.getUser().forEach((key, value) -> oAuthProviders.put(key,
            new OAuthProvider(value, properties.getProvider().get(key))));

        return oAuthProviders;
    }
}
