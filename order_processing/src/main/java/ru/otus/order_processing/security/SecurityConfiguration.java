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
        http
                .csrf(AbstractHttpConfigurer::disable)
                .sessionManagement((session) -> session
                        .sessionCreationPolicy(SessionCreationPolicy.ALWAYS))
                .authorizeHttpRequests((authorize) -> authorize
                        .requestMatchers(HttpMethod.GET,"/").hasAnyRole("admin", "user")
                        .requestMatchers("/edit").hasRole("admin")
                        .requestMatchers("/create").hasRole("admin")
                        .requestMatchers("/info").hasAnyRole("admin", "user")
                        .requestMatchers("/api/authors").hasRole("admin")
                        .requestMatchers("/api/comments/*").hasAnyRole("admin", "user")
                        .requestMatchers("/api/genres").hasRole("admin")
                        .requestMatchers(HttpMethod.GET, "/api/books").hasAnyRole("admin", "user")
                        .requestMatchers(HttpMethod.GET, "/api/books/*").hasAnyRole("admin", "user")
                        .requestMatchers(HttpMethod.POST, "/api/books").hasRole("admin")
                        .requestMatchers(HttpMethod.DELETE, "/api/books/*").hasRole("admin")
                        .anyRequest().authenticated()
                )
                .formLogin(Customizer.withDefaults());
        return http.build();
    }


    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder(10);
    }
}
