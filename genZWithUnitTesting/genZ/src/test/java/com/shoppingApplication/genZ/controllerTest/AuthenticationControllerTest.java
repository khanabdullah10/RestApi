package com.shoppingApplication.genZ.controllerTest;

import com.shoppingApplication.genZ.controller.AuthenticationController;
import com.shoppingApplication.genZ.model.User;
import com.shoppingApplication.genZ.service.UserService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.util.Arrays;
import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

class AuthenticationControllerTest {

    @InjectMocks
    private AuthenticationController authenticationController;

    @Mock
    private UserService userService;

    private MockMvc mockMvc;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        mockMvc = MockMvcBuilders.standaloneSetup(authenticationController).build();
    }

    // Test for Register User
    @Test
    void testRegisterUser() throws Exception {
        User user = new User("testuser", "password123", "testuser@example.com", "USER");

        // Mocking the service call
        doNothing().when(userService).registerUser(any(User.class));

        mockMvc.perform(post("/auth/register")
                        .contentType("application/json")
                        .content("{\"username\": \"testuser\", \"password\": \"password123\", \"email\": \"testuser@example.com\", \"role\": \"USER\"}"))
                .andExpect(status().isOk())
                .andExpect(content().string("User registered successfully"));

        // Verify that the register method was called once
        verify(userService, times(1)).registerUser(any(User.class));
    }

    // Test for Login User - Success
    @Test
    void testLoginUser_Success() throws Exception {
        // Mocking successful login
        when(userService.loginUser("testuser", "password123")).thenReturn(true);

        mockMvc.perform(post("/auth/login")
                        .header("username", "testuser")
                        .header("password", "password123"))
                .andExpect(status().isOk())
                .andExpect(content().string("Login successful"));

        // Verify that the login method was called once
        verify(userService, times(1)).loginUser("testuser", "password123");
    }

    // Test for Login User - Failure
    @Test
    void testLoginUser_Failure() throws Exception {
        // Mocking failed login
        when(userService.loginUser("testuser", "wrongpassword")).thenReturn(false);

        mockMvc.perform(post("/auth/login")
                        .header("username", "testuser")
                        .header("password", "wrongpassword"))
                .andExpect(status().isUnauthorized())
                .andExpect(content().string("Invalid username or password"));

        // Verify that the login method was called once
        verify(userService, times(1)).loginUser("testuser", "wrongpassword");
    }

    // Test for Get User by Email
    @Test
    void testGetUserByEmail() throws Exception {
        User user = new User("testuser", "password123", "testuser@example.com", "USER");

        // Mocking the service call
        when(userService.getUserByEmail("testuser@example.com")).thenReturn(user);

        mockMvc.perform(get("/auth/{email}", "testuser@example.com"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.username").value("testuser"))
                .andExpect(jsonPath("$.email").value("testuser@example.com"));

        // Verify that the getUserByEmail method was called once
        verify(userService, times(1)).getUserByEmail("testuser@example.com");
    }

    // Test for Validate Email - Success
    @Test
    void testValidateEmail_Success() throws Exception {
        // Mocking the service call for a valid email
        when(userService.isEmailRegistered("testuser@example.com")).thenReturn(true);

        mockMvc.perform(get("/auth/validate-email")
                        .header("email", "testuser@example.com"))
                .andExpect(status().isOk())
                .andExpect(content().string("Email is valid as it is registered."));

        // Verify that the isEmailRegistered method was called once
        verify(userService, times(1)).isEmailRegistered("testuser@example.com");
    }

    // Test for Validate Email - Failure
    @Test
    void testValidateEmail_Failure() throws Exception {
        // Mocking the service call for an invalid email
        when(userService.isEmailRegistered("nonexistent@example.com")).thenReturn(false);

        mockMvc.perform(get("/auth/validate-email")
                        .header("email", "nonexistent@example.com"))
                .andExpect(status().isNotFound())
                .andExpect(content().string("Invalid : Email is not registered."));

        // Verify that the isEmailRegistered method was called once
        verify(userService, times(1)).isEmailRegistered("nonexistent@example.com");
    }

    // Test for Get All Users
    @Test
    void testGetAllUsers() throws Exception {
        List<User> users = Arrays.asList(
                new User("testuser1", "password1", "testuser1@example.com", "USER"),
                new User("testuser2", "password2", "testuser2@example.com", "USER")
        );

        // Mocking the service call
        when(userService.getAllUsers()).thenReturn(users);

        mockMvc.perform(get("/auth/fetchAllUsers"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.size()").value(2));

        // Verify that the getAllUsers method was called once
        verify(userService, times(1)).getAllUsers();
    }
}
