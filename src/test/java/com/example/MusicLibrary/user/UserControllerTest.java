package com.example.MusicLibrary.user;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;


@SpringBootTest
@AutoConfigureMockMvc
class UserControllerTest {

    @MockBean
    private UserService userService;
    @Autowired
    private MockMvc mockMvc;
    @Autowired
    private ObjectMapper objectMapper;

    @Test
    void shouldGetAllUsers() throws Exception {
        User user = new User(1L, "shazil", "333", "USER");
        User user1 = new User(2L, "hanzala", "333", "USER");

        List<User> list = new ArrayList<>();
        list.add(user);
        list.add(user1);

        when(userService.getUsers()).thenReturn(list);

        this.mockMvc.perform(get("http://localhost:8080/api/v1/music/user")
                        .with(SecurityMockMvcRequestPostProcessors.user("User 1").password("123").roles("ADMIN")))
                        .andExpect(status().isOk())
                        .andExpect(jsonPath("$.size()").value(list.size()));

    }

    @Test
    void shouldGetUserByIdWithAdminRole() throws Exception {
        User user = new User(1L, "shazil", "333", "USER");

        when(userService.getUserById(anyLong())).thenReturn(user);

        this.mockMvc.perform(get("http://localhost:8080/api/v1/music/user/{user_Id}", user.getUser_Id())
                .with(SecurityMockMvcRequestPostProcessors.user("User 1").password("123").roles("ADMIN")))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.userName").value(user.getUserName()))
                .andExpect(jsonPath("$.user_Id").value(user.getUser_Id()))
                .andExpect(jsonPath("$.password").value(user.getPassword()))
                .andExpect(jsonPath("$.role").value(user.getRole()));
    }

    @Test
    void shouldCreateUser() throws Exception {
        User user = new User(1L, "shazil", "333", "USER");

        when(userService.createUser(any(User.class))).thenReturn(user);

        this.mockMvc.perform(post("http://localhost:8080/api/v1/music/user")
                        .with(SecurityMockMvcRequestPostProcessors.user("User 1").password("123").roles("ADMIN"))
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(user)))
                        .andExpect(status().isOk())
                        .andExpect(jsonPath("$.userName").value(user.getUserName()));
    }

    //Todo this test is not running properly
    @Test
    @Disabled
    void updateUser() throws Exception {
        User user = new User(1L, "shazil", "333", "USER");

        MvcResult mvcResult = mockMvc
                .perform(
                        put("http://localhost:8080/api/v1/music/user/{user_Id}",
                                user.getUser_Id())
                        .with(SecurityMockMvcRequestPostProcessors.user("User 1").password("123").roles("USER"))
                        .contentType(MediaType.APPLICATION_JSON))
                .andReturn();

        int status = mvcResult.getResponse().getStatus();
        assertEquals(200, status);
    }

    //Todo this test is not running properly
    @Test
    @Disabled
    void deleteUser() throws Exception {
        User user = new User(1L, "shazil", "333", "USER");

        MvcResult mvcResult = mockMvc.perform(delete("http://localhost:8080/api/v1/music/user/{user_Id}", user.getUser_Id())
                .with(SecurityMockMvcRequestPostProcessors.user("User 1").password("123").roles("USER"))
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(String.valueOf(user)))
                .andReturn();
    }
}