package com.Atm.Atm.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
public class SecurityConfig {

    @Autowired
    private JwtFilter jwtFilter;

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {

        http
            .csrf(csrf -> csrf.disable())
            .cors(cors -> cors.disable())
            .sessionManagement(sm ->
                sm.sessionCreationPolicy(SessionCreationPolicy.STATELESS)
            )

            .authorizeHttpRequests(auth -> auth

                // Static UI
                .requestMatchers(
                    "/",
                    "/index.html",
                    "/atm-ui.html",
                    "/css/**",
                    "/js/**",
                    "/images/**"
                ).permitAll()

                // Public APIs
                .requestMatchers(
                    "/atm/create",
                    "/atm/login",
                    "/atm/send-otp",
                    "/atm/verify-otp"
                ).permitAll()

                // Protected APIs
                .requestMatchers(
                    "/atm/deposit",
                    "/atm/withdraw",
                    "/atm/update-pin",
                    "/atm/delete"
                ).authenticated()

                .anyRequest().permitAll()
            );

        http.addFilterBefore(jwtFilter, UsernamePasswordAuthenticationFilter.class);

        return http.build();
    }
}



