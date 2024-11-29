package com.blog.demo.config;

import com.blog.demo.service.CustomUserDetailsService;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
public class SecurityConfig {

    private final CustomUserDetailsService userDetailsService;

    // Constructor Injection for CustomUserDetailsService
    public SecurityConfig(CustomUserDetailsService userDetailsService) {
        this.userDetailsService = userDetailsService;
    }

    // Password Encoder Bean for BCrypt
    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    // Authentication manager bean
    @Bean
    public AuthenticationManager authenticationManager(HttpSecurity http) throws Exception {
        AuthenticationManagerBuilder auth = http.getSharedObject(AuthenticationManagerBuilder.class);
        auth.userDetailsService(userDetailsService).passwordEncoder(passwordEncoder());
        return auth.build();
    }

    // Security filter chain bean with Lambda DSL
    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
                .csrf(csrf -> csrf.ignoringRequestMatchers("/h2-console/**")) // Exclude CSRF protection for H2 console (if you're using it)
                .authorizeHttpRequests(authorizeHttpRequests -> authorizeHttpRequests
                        .requestMatchers("/admin/**").hasRole("ADMIN")  // Admin pages require "ADMIN" role
                        .requestMatchers("/user/**").hasAnyRole("USER", "ADMIN") // User pages require "USER" or "ADMIN" role
                        .requestMatchers("/login", "/register", "/css/**", "/js/**").permitAll()  // Allow unauthenticated access
                        .anyRequest().authenticated()  // All other pages require authentication
                )
                .formLogin(formLogin -> formLogin
                        .loginPage("/login")  // Custom login page
                        .usernameParameter("email")  // Set username field to "email"
                        .defaultSuccessUrl("/", true)  // Redirect to home page after successful login
                )
                .logout(logout -> logout
                        .logoutUrl("/logout")  // Custom logout URL
                        .logoutSuccessUrl("/login?logout")  // Redirect to login page after successful logout
                        .permitAll()
                )
                .requiresChannel(channel -> channel
                        .requestMatchers("/login", "/register").requiresSecure() // Force HTTPS for login and register pages (if SSL is enabled)
                );

        return http.build();
    }
}
