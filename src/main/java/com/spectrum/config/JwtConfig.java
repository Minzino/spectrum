package com.spectrum.config;

import com.spectrum.auth.JwtProperties;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Configuration;

@Configuration
@RequiredArgsConstructor
@EnableConfigurationProperties(JwtProperties.class)
public class JwtConfig {

}
