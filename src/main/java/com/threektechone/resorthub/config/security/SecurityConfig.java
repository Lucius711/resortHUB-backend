package com.threektechone.resorthub.config.security;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;


@Configuration
@EnableWebSecurity
public class SecurityConfig {

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
            .csrf(csrf -> csrf.disable())
            .authorizeHttpRequests(auth -> auth
                .requestMatchers("/api/auth/**").permitAll() // Allow unauthenticated access to authentication endpoints
                .requestMatchers("/api/admin/**").hasRole("ADMIN") // Restrict access to admin endpoints to users with ADMIN role
                .requestMatchers("/api/owner/**").hasRole("OWNER") // Allow access to owner endpoints for users with OWNER role
                .requestMatchers("/api/staff/**").hasRole("STAFF") // Allow access to staff endpoints for users with STAFF role
                .requestMatchers("/api/customer/**").hasRole("CUSTOMER") // Allow access to customer endpoints for users with CUSTOMER role
                .anyRequest().authenticated() // Require authentication for all other endpoints
            )
            .sessionManagement(session -> session
                .sessionCreationPolicy(SessionCreationPolicy.STATELESS) // Use stateless sessions (JWT)
            );

        return http.build();
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
    
}
