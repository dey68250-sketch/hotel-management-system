package com.hotel_management.config;

import com.hotel_management.security.JwtAuthenticationFilter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
public class SecurityConfig {

    private final JwtAuthenticationFilter jwtAuthenticationFilter;

    public SecurityConfig(JwtAuthenticationFilter jwtAuthenticationFilter) {
        this.jwtAuthenticationFilter = jwtAuthenticationFilter;
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public SecurityFilterChain securityFilterChain(
            HttpSecurity http) throws Exception {

        http
                .csrf(csrf -> csrf.disable())
                .authorizeHttpRequests(auth -> auth

                        // Public APIs
                        .requestMatchers("/uploads/**")
                        .permitAll()

                        .requestMatchers(HttpMethod.GET,
                                "/api/rooms/**")
                        .permitAll()

                        .requestMatchers(HttpMethod.POST,
                                "/api/bookings")
                        .permitAll()

                        .requestMatchers("/api/auth/**")
                        .permitAll()

                        .requestMatchers(
                                HttpMethod.GET,
                                "/api/payments/info"
                        ).permitAll()

                        .requestMatchers(
                                HttpMethod.POST,
                                "/api/payments/*/upload"
                        ).permitAll()

                        .requestMatchers(
                                HttpMethod.GET,
                                "/api/bookings/*/receipt"
                        )
                        .permitAll()

                        // Admin APIs
                        .requestMatchers(HttpMethod.POST,
                                "/api/rooms/**")
                        .hasAuthority("ADMIN")

                        .requestMatchers(HttpMethod.PUT,
                                "/api/rooms/**")
                        .hasAuthority("ADMIN")

                        .requestMatchers(HttpMethod.DELETE,
                                "/api/rooms/**")
                        .hasAuthority("ADMIN")

                        .requestMatchers("/api/bookings/**")
                        .hasAuthority("ADMIN")

                        .requestMatchers("/api/admin/**")
                        .hasAuthority("ADMIN")

                        .requestMatchers("/api/payments/pending")
                        .hasAuthority("ADMIN")

                        .requestMatchers(
                                HttpMethod.PUT,
                                "/api/payments/*/verify"
                        ).hasAuthority("ADMIN")

                        .requestMatchers(
                                HttpMethod.PUT,
                                "/api/bookings/*/checkout"
                        )
                        .hasAuthority("ADMIN")

                        .requestMatchers(
                                HttpMethod.GET,
                                "/api/bookings/history"
                        )
                        .hasAuthority("ADMIN")

                        .anyRequest()
                        .authenticated()
                )
                .addFilterBefore(
                        jwtAuthenticationFilter,
                        UsernamePasswordAuthenticationFilter.class
                );

        return http.build();
    }
}
