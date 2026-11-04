/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.colegiotomasbaras.seguridad;

import java.util.List;
import java.util.Locale;
import java.util.Set;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;

import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;

import org.springframework.security.core.authority.SimpleGrantedAuthority;

import org.springframework.security.oauth2.server.resource.authentication.JwtAuthenticationConverter;

import org.springframework.security.web.SecurityFilterChain;

import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;




/**
 *
 * @author Maximo Gallardo
 */
@Configuration
public class SeguridadConfig {

    @Bean
    public SecurityFilterChain seguridad(
            HttpSecurity http,
            JwtAuthenticationConverter converter
    ) throws Exception {

        http
            .cors(Customizer.withDefaults())

            .csrf(AbstractHttpConfigurer::disable)

            .sessionManagement(session ->
                session.sessionCreationPolicy(
                    SessionCreationPolicy.STATELESS
                )
            )

            .authorizeHttpRequests(auth -> auth

                .requestMatchers(
                    HttpMethod.OPTIONS, "/**"
                ).permitAll()

                .requestMatchers(
                    HttpMethod.POST, "/api/auth/login"
                ).permitAll()

                .requestMatchers(
                    HttpMethod.POST, "/api/auth/registro"
                ).hasRole("DIRECTIVO")

                .requestMatchers(
                    HttpMethod.GET,
                    "/api/alumnos",
                    "/api/alumnos/**"
                ).hasRole("DIRECTIVO")

                .requestMatchers(
                    HttpMethod.GET,
                    "/api/cursos",
                    "/api/cursos/**"
                ).hasAnyRole("DIRECTIVO", "DOCENTE")
                    
                    .requestMatchers(
                    HttpMethod.GET,
                    "/api/asistencias",
                    "/api/asistencias/**"
                ).hasAnyRole("DIRECTIVO")

                .requestMatchers(
                    HttpMethod.POST,
                    "/api/alumnos",
                    "/api/alumnos/*/matricular",
                    "/api/evaluaciones-modulo"
                ).hasRole("DIRECTIVO")

                .anyRequest().denyAll()
            )

            .oauth2ResourceServer(oauth ->
                oauth.jwt(jwt ->
                    jwt.jwtAuthenticationConverter(converter)
                )
            );

        return http.build();
    }

    @Bean
    public JwtAuthenticationConverter jwtAuthenticationConverter() {

        JwtAuthenticationConverter converter =
                new JwtAuthenticationConverter();

        converter.setJwtGrantedAuthoritiesConverter(jwt -> {

            String rol = jwt.getClaimAsString("rol");

            if (rol == null) {
                return List.of();
            }

            rol = rol.toLowerCase(Locale.ROOT);

            if (!Set.of(
                    "directivo", "docente", "preceptor"
            ).contains(rol)) {
                return List.of();
            }

            return List.of(
                new SimpleGrantedAuthority(
                    "ROLE_" + rol.toUpperCase(Locale.ROOT)
                )
            );
        });

        return converter;
    }

    @Bean
    public CorsConfigurationSource corsConfigurationSource() {

        CorsConfiguration cors = new CorsConfiguration();

        cors.setAllowedOrigins(
                List.of("http://localhost:5173")
        );

        cors.setAllowedMethods(
                List.of(
                    "GET", "POST", "PUT",
                    "PATCH", "DELETE", "OPTIONS"
                )
        );

        cors.setAllowedHeaders(
                List.of("Authorization", "Content-Type")
        );

        UrlBasedCorsConfigurationSource source =
                new UrlBasedCorsConfigurationSource();

        source.registerCorsConfiguration("/api/**", cors);

        return source;
    }
}