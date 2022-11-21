package com.example.MusicLibrary.user;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;

@ExtendWith(MockitoExtension.class)
class UserServiceTest {
    @Mock
    private PasswordEncoder passwordEncoder;
    @Mock
    private UserRepository userRepository;

    private UserService underTest;

    @BeforeEach
    void setUp(){
        underTest = new UserService(passwordEncoder, userRepository);
    }

    @Test
    void canGetUsers() {
        underTest.getUsers();

        verify(userRepository).findAll();
    }

    @Test
    void canGetUserWithId(){
        User user = new User(1L, "shazil", "123", "USER");
        given(userRepository.findById(user.getUser_Id())).willReturn(Optional.of(user));

        underTest.getUserById(user.getUser_Id());

        verify(userRepository).findById(user.getUser_Id());
        assertThat(user.getUserName()).isEqualTo("shazil");
    }

    @Test
    void willThrowWhenGettingUserWithId(){
        User user = new User();

        assertThatThrownBy(() -> underTest.getUserById(user.getUser_Id()))
                .isInstanceOf(IllegalStateException.class)
                .hasMessage("User with id "+ user.getUser_Id() + " does not exists");
    }

    @Test
    void canCreateUser() {
        User user = new User("shazil", "123", "USER");

        underTest.createUser(user);

        ArgumentCaptor<User> captor = ArgumentCaptor.forClass(User.class);
        verify(userRepository).save(captor.capture());
        User capturedUser = captor.getValue();
        assertThat(capturedUser).isEqualTo(user);
    }

    @Test
    void willThrowWhenCreatingUser(){
        User user = new User("Taha", "123", "ADMIN");
        given(userRepository.findByUserName(user.getUserName())).willReturn(Optional.of(user));

        assertThatThrownBy(() -> underTest.createUser(user))
                .isInstanceOf(IllegalStateException.class)
                .hasMessage("User with Username "+user.getUserName()+" already exist");

        verify(passwordEncoder, never()).encode(any());
        verify(userRepository, never()).save(any());
    }

    @Test
    void willUpdateUser() {
        User user = new User(1L,"Taha", "123", "ADMIN");
        given(userRepository.findById(user.getUser_Id())).willReturn(Optional.of(user));

        underTest.updateUser(1L, "Talha", "1123", "USER");

        assertThat(userRepository.findById(user.getUser_Id())).isEqualTo(Optional.of(user));
        assertThat(user.getUserName()).isEqualTo("Talha");
    }

    @Test
    public void willThrowUserAlreadyExistsWhenUpdating(){
        User user = new User(1L,"Taha", "123", "ADMIN");
        given(userRepository.findById(user.getUser_Id())).willReturn(Optional.of(user));
        given(userRepository.findByUserName(user.getUserName())).willReturn(Optional.of(user));

        assertThatThrownBy(() -> underTest.updateUser(user.getUser_Id(), user.getUserName(), user.getPassword(), user.getRole()))
                .isInstanceOf(IllegalStateException.class)
                .hasMessage("User with Username "+user.getUserName()+" already exist");
    }
    @Test
    void willDeleteUser() {
        User user = new User(1L,"Taha", "123", "ADMIN");
        given(userRepository.existsById(user.getUser_Id())).willReturn(true);

        underTest.deleteUser(user.getUser_Id());

        verify(userRepository).deleteById(user.getUser_Id());
    }

    @Test
    void willNotDeleteUserAndThrowDoesNotExists(){
        User user = new User(1L,"Taha", "123", "ADMIN");

        assertThatThrownBy(() -> underTest.deleteUser(user.getUser_Id())).isInstanceOf(IllegalStateException.class)
                .hasMessage("User does not exist");

        verify(userRepository, never()).deleteById(any());
    }
}