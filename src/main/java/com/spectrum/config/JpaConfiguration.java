package com.spectrum.config;

import com.spectrum.domain.post.Post;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@Configuration
@EnableJpaAuditing
@EntityScan(basePackageClasses = {Post.class})
public class JpaConfiguration {

}
