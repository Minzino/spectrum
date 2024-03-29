package com.spectrum.common.config;

import com.spectrum.common.auth.provider.InMemoryProviderRepository;
import com.spectrum.common.auth.OAuthProperties;
import com.spectrum.common.auth.provider.OAuthProvider;
import com.spectrum.common.auth.provider.OAuthProviderConverter;
import java.util.Map;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
@EnableConfigurationProperties(OAuthProperties.class)
public class OAuthConfig {
    private final OAuthProperties properties;

    public OAuthConfig(OAuthProperties properties) {
        this.properties = properties;
    }

    @Bean
    public InMemoryProviderRepository inMemoryProviderRepository() {
        Map<String, OAuthProvider> providers = OAuthProviderConverter.convertToOAuthProviders(properties);
        return new InMemoryProviderRepository(providers);
    }
}
