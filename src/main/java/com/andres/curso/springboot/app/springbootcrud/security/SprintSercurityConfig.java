package com.andres.curso.springboot.app.springbootcrud.security;


import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
public class SprintSercurityConfig {


    /*
        - Genera un componente Beans con una referencia de BCrypt
    */

    @Bean
    PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        return http.authorizeHttpRequests( (authz) -> authz
                                        .requestMatchers("/users").permitAll()
                                        .anyRequest().authenticated())
                .csrf(config -> config.disable())
                .sessionManagement(magnament -> magnament.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
                .build();

    }
}