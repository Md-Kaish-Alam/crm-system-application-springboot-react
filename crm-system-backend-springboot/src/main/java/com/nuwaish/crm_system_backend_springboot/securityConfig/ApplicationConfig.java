package com.nuwaish.crm_system_backend_springboot.securityConfig;

import jakarta.servlet.http.HttpServletRequest;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.www.BasicAuthenticationFilter;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;

import java.util.Arrays;
import java.util.Collections;

@Configuration
public class ApplicationConfig {

    @SuppressWarnings("deprecation")
    @Bean
    SecurityFilterChain filterChain(HttpSecurity httpSecurity) throws Exception {
        httpSecurity.sessionManagement(management -> management.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
                .authorizeHttpRequests(
                        authorize -> authorize
                                .requestMatchers("/api/customers/**").permitAll() // Allow access to this endpoint
                                .requestMatchers("/api/**").authenticated() // Secure other API endpoints
                                .anyRequest().permitAll()) // Permit all other requests
                .addFilterBefore(new JwtTokenValidator(), BasicAuthenticationFilter.class) // JWT token validation
                .csrf(csrf -> csrf.disable()) // Disable CSRF protection
                .cors(cors -> cors.configurationSource(corsConfigurationSource())); // Configure CORS policy

        return httpSecurity.build();
    }

    private CorsConfigurationSource corsConfigurationSource() {
        return new CorsConfigurationSource() {
            @Override
            public CorsConfiguration getCorsConfiguration(HttpServletRequest request) {
                CorsConfiguration ccfg = new CorsConfiguration();
                ccfg.setAllowedOrigins(Arrays.asList("http://localhost:5173"));
                ccfg.setAllowedMethods(Collections.singletonList("*"));
                ccfg.setAllowCredentials(true);
                ccfg.setAllowedHeaders(Collections.singletonList("*"));
                ccfg.setExposedHeaders(Arrays.asList("Authorization"));
                ccfg.setMaxAge(3600L);

                return ccfg;
            }
        };
    }

    @Bean
    PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
}
