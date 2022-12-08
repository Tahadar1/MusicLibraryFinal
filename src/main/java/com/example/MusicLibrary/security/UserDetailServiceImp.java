package com.example.MusicLibrary.security;

import com.example.MusicLibrary.user.User;
import com.example.MusicLibrary.user.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import java.util.Optional;

public class UserDetailServiceImp implements UserDetailsService {

    @Autowired
    private UserRepository userRepository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Optional<User> user = userRepository.findByUserName(username);
        User u = user.orElseThrow(() -> new UsernameNotFoundException("Username does not exists"));
        return new UserDetailImp(u);
    }
}
