package com.example.habitaciones.config;


import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
@EnableMethodSecurity   // 🔥 permite @PreAuthorize
public class SecurityConfig {

    @Bean
    SecurityFilterChain filterChain(HttpSecurity http) throws Exception {

        http
            .csrf(csrf -> csrf.disable())

            // 🔐 Todas las peticiones deben venir del Gateway
            .authorizeHttpRequests(auth -> auth
                .anyRequest().authenticated()
            )

            // 🔥 Agregamos filtro que lee roles desde headers
            .addFilterBefore(new HeaderAuthFilter(),
                    UsernamePasswordAuthenticationFilter.class);

        return http.build();
    }
}