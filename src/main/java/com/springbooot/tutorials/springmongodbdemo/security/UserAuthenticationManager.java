package com.springbooot.tutorials.springmongodbdemo.security;

import com.springbooot.tutorials.springmongodbdemo.model.SecurityUser;
import com.springbooot.tutorials.springmongodbdemo.model.User;
import com.springbooot.tutorials.springmongodbdemo.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

@RequiredArgsConstructor
public class UserAuthenticationManager implements AuthenticationManager {
    private final String username;
    private final String password;
    private final UserService userService;
    private final PasswordEncoder passwordEncoder;

    @Override
    public Authentication authenticate(Authentication authentication) throws AuthenticationException {
        UserAuthentication userAuthentication = (UserAuthentication) authentication;

        var user = userService.loadUserByUsername(username);

        if(passwordEncoder.matches(password, user.getPassword())){
            userAuthentication.setAuthenticated(true);
        }
        return userAuthentication;
    }
}
