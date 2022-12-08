package com.example.MusicLibrary.user;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

@Service
public class UserService {
    private final PasswordEncoder passwordEncoder;

    private final UserRepository userRepository;

    @Autowired
    public UserService(PasswordEncoder passwordEncoder, UserRepository userRepository) {
        this.passwordEncoder = passwordEncoder;
        this.userRepository = userRepository;
    }

    public List<User> getUsers() {
        return userRepository.findAll();
    }

    public User getUserById(Long id) {
        User user = userRepository.findById(id).orElseThrow(() -> new IllegalStateException("User with id " + id + " does not exists"));
        return user;
    }

    public User createUser(User user) {
        Optional<User> optionalUser = userRepository.findByUserName(user.getUserName());
        if (optionalUser.isPresent()) {
            throw new IllegalStateException("User with Username " + user.getUserName() + " already exist");
        }
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        user.setRole("USER");
        return userRepository.save(user);
    }

    @Transactional
    public void updateUser(Long user_id, String userName, String password, String role) {
        User user = userRepository.findById(user_id).orElseThrow(() -> new IllegalStateException("User does not exist"));
        if (userName != null && userName.length() > 0 || !Objects.equals(user.getUserName(), userName)) {
            Optional<User> optionalUser = userRepository.findByUserName(userName);
            if (optionalUser.isPresent()) {
                throw new IllegalStateException("User with Username " + userName + " already exist");
            }
            user.setUserName(userName);
        }
        if (password != null && password.length() > 0 && !Objects.equals(user.getPassword(), password)) {
            user.setPassword(passwordEncoder.encode(password));
        }
        if (role != null && role.length() > 0 && !Objects.equals(user.getRole(), role)) {
            user.setRole(role);
        }
    }

    @Transactional
    public void updateUserFullBody(User user, Long user_Id){
        Optional<User> user1 = userRepository.findById(user_Id);
        Optional<User> user2 = userRepository.findByUserName(user.getUserName());
        if(user1.isEmpty()){
            throw new IllegalStateException("User not found");
        }
        else if (user2.isPresent() && !Objects.equals(user1.get().getUserName(), user.getUserName())){
            throw new IllegalStateException("User with username already exists");
        }
        else {
            user1.get().setUserName(user.getUserName());
            user1.get().setPassword(passwordEncoder.encode(user.getPassword()));
            user1.get().setRole(user.getRole());
        }
    }

    public void deleteUser(Long user_id) {
        boolean exists = userRepository.existsById(user_id);
        if (!exists) {
            throw new IllegalStateException("User does not exist");
        }
        userRepository.deleteById(user_id);
    }
}