package com.example.MusicLibrary.user;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.List;

@Configuration
public class UserConfig {
    @Autowired
    private PasswordEncoder passwordEncoder;

    @Bean
    CommandLineRunner commandLineRunnerUser(UserRepository repository) {
        return (args) -> {
            User user1 = new User("User 1", passwordEncoder.encode("123"), "ADMIN");
            User user2 = new User("User 2", passwordEncoder.encode("123"), "USER");
            repository.saveAll(List.of(user1, user2));
        };
    }
}
