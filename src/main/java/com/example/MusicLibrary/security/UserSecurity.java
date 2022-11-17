package com.example.MusicLibrary.security;

import com.example.MusicLibrary.user.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Component;

import java.util.Objects;

@Component("userSecurity")
public class UserSecurity {
    @Autowired
    UserRepository userRepository;

    public Boolean hasUserId(Authentication authentication, Long userId){
        Long userId1 = userRepository.findByUserName(authentication.getName()).get().getUser_Id();
        return Objects.equals(userId, userId1);
    }
}