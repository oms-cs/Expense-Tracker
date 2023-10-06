package com.springbooot.tutorials.springmongodbdemo.controller;

import com.springbooot.tutorials.springmongodbdemo.dto.AuthenticateRequestDto;
import com.springbooot.tutorials.springmongodbdemo.dto.AuthenticateResponseDto;
import com.springbooot.tutorials.springmongodbdemo.model.User;
import com.springbooot.tutorials.springmongodbdemo.security.UserAuthentication;
import com.springbooot.tutorials.springmongodbdemo.security.UserAuthenticationManager;
import com.springbooot.tutorials.springmongodbdemo.service.UserService;
import com.springbooot.tutorials.springmongodbdemo.util.JwtUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.DisabledException;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor
public class AuthController {
    private final JwtUtil jwtUtil;

    private final UserService userService;

    private final PasswordEncoder passwordEncoder;

    @GetMapping("/public")
    public String publicPage(){
        return "This is Public Page";
    }

    @PostMapping("/register")
    public ResponseEntity registerUser(@RequestBody User user){
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        User addedUser = userService.registerUser(user);
        return ResponseEntity.ok().body(addedUser.getUsername() + " : User Registered Successfully!");
    }

    @PostMapping("/authenticate")
    public ResponseEntity authenticationPage(@RequestBody AuthenticateRequestDto authenticateRequestDto){
        System.out.println("/authenticate");
        authenticate(authenticateRequestDto);
        UserDetails securityUser = null;
        try{
            securityUser =  userService.loadUserByUsername(authenticateRequestDto.getUsername());
        }catch (UsernameNotFoundException e){
            e.printStackTrace();
        }
        String jwtToken = jwtUtil.generateToken(securityUser.getUsername());

        return ResponseEntity.ok().body(new AuthenticateResponseDto(jwtToken));
    }

    public void authenticate(AuthenticateRequestDto authenticateRequestDto){
        UserAuthenticationManager userAuthenticationManager = new UserAuthenticationManager(authenticateRequestDto.getUsername(), authenticateRequestDto.getPassword(), userService, passwordEncoder );
        try {
            userAuthenticationManager.authenticate(new UserAuthentication());
        }catch (DisabledException e){
            e.printStackTrace();
        }catch (BadCredentialsException e) {
            e.printStackTrace();
        }
    }


}
