package com.qoobot.openmall.portal.config;

import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.security.web.authentication.SimpleUrlAuthenticationSuccessHandler;


import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * Spring Security 配置
 */
@Configuration
@EnableWebSecurity
@EnableMethodSecurity
@RequiredArgsConstructor
public class SecurityConfig {

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
            .csrf(AbstractHttpConfigurer::disable)
            .authorizeHttpRequests(auth -> auth
                .requestMatchers(
                    "/",
                    "/index",
                    "/css/**",
                    "/js/**",
                    "/images/**",
                    "/auth/register",
                    "/auth/do-register",
                    "/auth/login",
                    "/auth/do-login",
                    "/product/**",
                    "/category/**"
                ).permitAll()
                .requestMatchers(
                    "/cart/**",
                    "/order/**",
                    "/user/**",
                    "/auth/logout"
                ).authenticated()
                .anyRequest().permitAll()
            )
            .formLogin(form -> form
                .loginPage("/auth/login")
                .loginProcessingUrl("/auth/do-login")
                .usernameParameter("username")
                .passwordParameter("password")
                .successHandler(authenticationSuccessHandler())
                .failureUrl("/auth/login?error=true")
                .permitAll()
            )
            .logout(logout -> logout
                .logoutUrl("/auth/logout")
                .logoutSuccessUrl("/")
                .invalidateHttpSession(true)
                .clearAuthentication(true)
                .permitAll()
            );

        return http.build();
    }

    @Bean
    public AuthenticationSuccessHandler authenticationSuccessHandler() {
        return new SimpleUrlAuthenticationSuccessHandler() {
            @Override
            public void onAuthenticationSuccess(
                HttpServletRequest request,
                HttpServletResponse response,
                org.springframework.security.core.Authentication authentication
            ) throws IOException {
                response.sendRedirect("/");
            }
        };
    }
}
