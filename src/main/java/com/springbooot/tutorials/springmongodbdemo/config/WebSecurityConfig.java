package com.springbooot.tutorials.springmongodbdemo.config;

import com.springbooot.tutorials.springmongodbdemo.filter.UserAuthFilter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
public class WebSecurityConfig {

    @Autowired
    private UserAuthFilter userAuthFilter;

    @Bean
    public PasswordEncoder passwordEncoder(){
        return new BCryptPasswordEncoder();
    }

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
                .csrf(csrfCustomizer -> csrfCustomizer.disable())
                .authorizeHttpRequests(auth -> auth.requestMatchers("/auth/**").permitAll())
                .authorizeHttpRequests(authorize -> authorize.anyRequest().authenticated())
                .sessionManagement(sessionManager -> sessionManager.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
                .addFilterBefore(userAuthFilter, UsernamePasswordAuthenticationFilter.class);
        return http.build();
    }
}
