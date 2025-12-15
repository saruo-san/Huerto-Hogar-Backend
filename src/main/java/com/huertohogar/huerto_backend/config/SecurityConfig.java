package com.huertohogar.huerto_backend.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import com.huertohogar.huerto_backend.security.JwtAuthFilter;

@Configuration
@EnableGlobalMethodSecurity(prePostEnabled = true)
public class SecurityConfig {

    private final JwtAuthFilter jwtAuthFilter;

    public SecurityConfig(JwtAuthFilter jwtAuthFilter) {
        this.jwtAuthFilter = jwtAuthFilter;
    }

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http
            .csrf(csrf -> csrf.disable())
            .sessionManagement(sm -> sm.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
            .headers(h -> h.frameOptions().sameOrigin())
            .authorizeHttpRequests(auth -> auth
                .antMatchers("/api/auth/**", "/h2-console/**", "/actuator/**").permitAll()
                // Permitir cargar los assets de Swagger UI (HTML/CSS/JS)
                .antMatchers("/swagger-ui/**", "/swagger-ui.html").permitAll()
                // Permitir el documento OpenAPI públicamente para que Swagger UI pueda cargar la configuración
                .antMatchers("/v3/api-docs/**").permitAll()
                // Endpoints de pagos (flujo público de checkout en integración)
                .antMatchers("/api/payments/**").permitAll()
                .antMatchers("/api/products/**").permitAll()
                .anyRequest().authenticated()
            );

        http.addFilterBefore(jwtAuthFilter, UsernamePasswordAuthenticationFilter.class);
        return http.build();
    }
}
