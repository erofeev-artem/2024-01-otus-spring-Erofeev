package ru.otus.order.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.domain.AuditorAware;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;
import org.springframework.lang.NonNull;

import java.util.Optional;

@Configuration
@EnableJpaAuditing
public class AuditorConfig {

    @Bean
    public AuditorAware<String> auditorProvider() {
        return new AuditorBean();
    }

    private static class AuditorBean implements AuditorAware<String> {
        @Override
        @NonNull
        public Optional<String> getCurrentAuditor() {
            return Optional.empty();
        }
    }
}
