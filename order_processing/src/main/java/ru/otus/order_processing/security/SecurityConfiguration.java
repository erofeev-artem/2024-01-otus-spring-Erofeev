package ru.otus.order_processing.security;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
public class SecurityConfiguration {
    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http.csrf(AbstractHttpConfigurer::disable).sessionManagement((session) -> session
                        .sessionCreationPolicy(SessionCreationPolicy.ALWAYS))
                .authorizeHttpRequests((authorize) -> authorize
                        .requestMatchers(HttpMethod.GET, "/api/engineers")
                        .hasAnyRole("Administrator", "Operator")
                        .requestMatchers(HttpMethod.GET, "/api/tariffs")
                        .hasAnyRole("Administrator", "Operator", "Engineer")
                        .requestMatchers(HttpMethod.GET, "/api/orders/new")
                        .hasAnyRole("Administrator", "Operator")
                        .requestMatchers(HttpMethod.GET, "/api/orders/confirmed")
                        .hasAnyRole("Administrator", "Operator")
                        .requestMatchers(HttpMethod.GET, "/api/orders/completed")
                        .hasAnyRole("Administrator", "Operator")
                        .requestMatchers(HttpMethod.GET, "/api/orders/rejected")
                        .hasAnyRole("Administrator", "Operator")
                        .requestMatchers(HttpMethod.GET, "/api/orders/assigned")
                        .hasAnyRole("Administrator", "Engineer")
                        .requestMatchers(HttpMethod.POST, "/api/orders/")
                        .hasAnyRole("Administrator", "Operator")
                        .requestMatchers(HttpMethod.POST, "/api/orders/update")
                        .hasAnyRole("Administrator", "Engineer")
                        .anyRequest().authenticated()).formLogin(Customizer.withDefaults());
        return http.build();
    }


    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder(10);
    }
}
