package com.springbooot.tutorials.springmongodbdemo.config;

import com.springbooot.tutorials.springmongodbdemo.filter.UserAuthFilter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;
import org.springframework.web.cors.reactive.CorsConfigurationSource;

import java.util.Arrays;

@Configuration
public class WebSecurityConfig {

    @Autowired
    private UserAuthFilter userAuthFilter;

    @Bean
    public PasswordEncoder passwordEncoder(){
        return new BCryptPasswordEncoder();
    }

    private final String[] WHITE_LISTED_ENDPOINT = {
            "/auth/**",
            "/swagger-ui/**",
            "/v3/api-docs/**",
            "/swagger-ui.html",
            "/auth/register"
    };

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
                //disable Cross Site Request Forgery (Not Recommended for Production)
                .csrf(csrfCustomizer ->
                        csrfCustomizer.disable())

                //disable Cross Origin Requests (Not Recommended for Production)
                .cors(Customizer.withDefaults())

                // Authentication Not required for Auth and Documentation Endpoints
                .authorizeHttpRequests(
                        auth -> auth.requestMatchers(WHITE_LISTED_ENDPOINT).permitAll())
                // All Other Endpoints are Secured by Default
                .authorizeHttpRequests(
                        authorize -> authorize.anyRequest().authenticated())

                // Create Stateless Policy(i.e. Need to Authenticate in each Request)
                .sessionManagement(sessionManager ->
                        sessionManager.sessionCreationPolicy(SessionCreationPolicy.STATELESS))

                //Add jwt Auth filter in filter chain before username password authentication.
                .addFilterBefore(userAuthFilter, UsernamePasswordAuthenticationFilter.class);
        return http.build();
    }
}
