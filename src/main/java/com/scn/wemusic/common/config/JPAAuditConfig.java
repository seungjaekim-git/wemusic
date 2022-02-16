package com.scn.wemusic.common.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.domain.AuditorAware;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;
import org.springframework.security.core.context.SecurityContextHolder;

import java.util.Optional;

@Configuration
@EnableJpaAuditing(auditorAwareRef = "auditorProvider")
public class JPAAuditConfig {

    @Bean
    public AuditorAware<String> auditorProvider() {
        return new AuditorAware<String>() {

            @Override
            public Optional<String> getCurrentAuditor() {
                if (SecurityContextHolder.getContext().getAuthentication() != null) {
                    OAuth2Authentication auth = (OAuth2Authentication) SecurityContextHolder.getContext().getAuthentication();
                    Object principal = auth.getUserAuthentication().getPrincipal();
                    return userDetails.getUsername();
                } else {
                    return Optional.of("Unknown");
                }
            }
        };
    }
}