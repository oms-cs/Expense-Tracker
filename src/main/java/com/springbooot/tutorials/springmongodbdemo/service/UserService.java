package com.springbooot.tutorials.springmongodbdemo.service;

import com.springbooot.tutorials.springmongodbdemo.model.SecurityUser;
import com.springbooot.tutorials.springmongodbdemo.model.User;
import com.springbooot.tutorials.springmongodbdemo.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;


@Service
@RequiredArgsConstructor
public class UserService implements UserDetailsService {

    private final UserRepository userRepository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Optional<User> user = userRepository.findByUsername(username);
        return user.map(SecurityUser::new).orElseThrow(() -> new UsernameNotFoundException("User Not Found!!"));
    }

    public User registerUser(User user){
        user.setPassword(user.getPassword());
        userRepository.save(user);
        return user;
    }
}
