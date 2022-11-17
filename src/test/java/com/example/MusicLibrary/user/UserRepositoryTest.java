package com.example.MusicLibrary.user;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest
class UserRepositoryTest {

    @Autowired
    private UserRepository underTest;

    @Test
    void itShouldFindUserByUsername() {
        String username = "Taha";
        User user = new User(username, "123","ADMIN");
        underTest.save(user);

        Optional<User> foundUser = underTest.findByUserName(username);

        assertThat(foundUser).isNotEmpty();
        assertThat(foundUser.get().getUserName()).isEqualTo(username);
    }

    @Test
    void itShouldNotFindUserByUsername(){
        String username = "Taha";

        Optional<User> foundUser = underTest.findByUserName(username);

        assertThat(foundUser).isEmpty();
    }
}