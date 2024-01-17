package com.springbooot.tutorials.springmongodbdemo.controller;

import com.springbooot.tutorials.springmongodbdemo.dto.AuthenticateRequestDto;
import com.springbooot.tutorials.springmongodbdemo.dto.AuthenticateResponseDto;
import com.springbooot.tutorials.springmongodbdemo.exception.UserAlreadyExists;
import com.springbooot.tutorials.springmongodbdemo.model.CustomErrorResponse;
import com.springbooot.tutorials.springmongodbdemo.model.User;
import com.springbooot.tutorials.springmongodbdemo.security.UserAuthentication;
import com.springbooot.tutorials.springmongodbdemo.security.UserAuthenticationManager;
import com.springbooot.tutorials.springmongodbdemo.service.UserService;
import com.springbooot.tutorials.springmongodbdemo.util.JwtUtil;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.DisabledException;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;

@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor
@Log4j2
@Validated
public class AuthController {

    private final JwtUtil jwtUtil; // Inject JWTUtil via constructor

    private final UserService userService; // Inject UserService via Constructor

    private final PasswordEncoder passwordEncoder;

    @GetMapping("/public")
    public String publicPage(){
        log.info("Accessing Public Endpoint.");
        return "This is Public Page";
    }

    @PostMapping("/register")
    public ResponseEntity registerUser(@Valid @RequestBody User user){
        log.info("Register User Endpoint {} ",user.getUsername());
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        User addedUser = null;
        try{
            addedUser = userService.registerUser(user);
        }catch (UserAlreadyExists ex){
            log.error(ex.getMessage());
            log.error("exception in [{}] : {}",ex.getClass(),ex);
            CustomErrorResponse error = new CustomErrorResponse(HttpStatus.BAD_REQUEST.toString(), ex.getMessage());
            error.setTimestamp(LocalDateTime.now());
            error.setStatus(HttpStatus.BAD_REQUEST.value());
            return ResponseEntity.badRequest().body(error);
        }
        log.info("User Registered Successfully");
        return ResponseEntity.ok().body(addedUser.getUsername() + " : User Registered Successfully!");
    }

    @PostMapping("/authenticate")
    public ResponseEntity authenticationPage(@RequestBody AuthenticateRequestDto authenticateRequestDto){
        System.out.println("/authenticate");
        try{
            authenticate(authenticateRequestDto);
        }catch (Exception e){
            log.error("Invalid Credentials!!");
            log.error("exception in [{}] : {}",e.getClass(),e);
            e.printStackTrace();
            return ResponseEntity.badRequest().body("Invalid Credentials!!");
        }

        UserDetails securityUser = null;
        try{
            securityUser =  userService.loadUserByUsername(authenticateRequestDto.getUsername());
        }catch (UsernameNotFoundException e){
            log.error("{} User Not Found!!", authenticateRequestDto.getUsername());
            e.printStackTrace();
        }
        String jwtToken = jwtUtil.generateToken(securityUser.getUsername());
            log.info("Token Generated!!");
        return ResponseEntity.ok().body(new AuthenticateResponseDto(jwtToken));
    }

    public void authenticate(AuthenticateRequestDto authenticateRequestDto){
        log.info("Authenticate User!!");
        log.info("Authentication Manager Initiated!");
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
