package com.spectrum.common.auth;

import com.spectrum.common.auth.dto.ProviderPropertiesDto;
import com.spectrum.common.auth.dto.UserPropertiesDto;
import java.util.Map;
import javax.validation.constraints.NotNull;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.context.properties.ConstructorBinding;
import org.springframework.validation.annotation.Validated;

@Getter
@ConstructorBinding
@Validated
@RequiredArgsConstructor
@ConfigurationProperties(prefix = "oauth2")
public class OAuthProperties {

    @NotNull
    private final Map<String, UserPropertiesDto> user;
    @NotNull
    private final Map<String, ProviderPropertiesDto> provider;
}
