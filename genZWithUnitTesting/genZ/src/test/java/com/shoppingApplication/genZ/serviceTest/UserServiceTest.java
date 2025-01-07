package com.shoppingApplication.genZ.serviceTest;
import com.shoppingApplication.genZ.model.User;
import com.shoppingApplication.genZ.repositoryTest.UserRepository;
import com.shoppingApplication.genZ.service.UserService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.*;

import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class UserServiceTest {

    @InjectMocks
    private UserService userService;

    @Mock
    private UserRepository userRepository;

    private User user;

    @BeforeEach
    void setUp() {
        // Initialize Mockito annotations and create a sample user
        MockitoAnnotations.openMocks(this);
        user = new User();
        user.setEmail("test@example.com");
        user.setUsername("testUser");
        user.setPassword("password123");
    }

    @Test
    void testRegisterUser() {
        // Arrange: Mock the registerUser method to do nothing when called
        doNothing().when(userRepository).registerUser(any(User.class));

        // Act: Call the registerUser method
        userService.registerUser(user);

        // Assert: Verify that registerUser was called with the correct argument
        verify(userRepository, times(1)).registerUser(any(User.class));
    }

    @Test
    void testGetUserByEmail() {
        // Arrange: Mock the findUserByEmail method to return a user for a specific email
        when(userRepository.findUserByEmail("test@example.com")).thenReturn(user);

        // Act: Call the getUserByEmail method
        User result = userService.getUserByEmail("test@example.com");

        // Assert: Verify that the returned user matches the expected user
        assertNotNull(result);
        assertEquals("test@example.com", result.getEmail());
    }

    @Test
    void testGetAllUsers() {
        // Arrange: Mock the findAll method to return a list of users
        List<User> users = Arrays.asList(user);
        when(userRepository.findAll()).thenReturn(users);

        // Act: Call the getAllUsers method
        List<User> result = userService.getAllUsers();

        // Assert: Verify the returned list contains the expected users
        assertNotNull(result);
        assertEquals(1, result.size());
        assertEquals("test@example.com", result.get(0).getEmail());
    }

    @Test
    void testLoginUser_SuccessfulLogin() {
        // Arrange: Mock the login method to return a user when provided with correct credentials
        when(userRepository.login("testUser", "password123")).thenReturn(user);

        // Act: Call the loginUser method
        boolean result = userService.loginUser("testUser", "password123");

        // Assert: Verify that the login is successful
        assertTrue(result);
    }

    @Test
    void testLoginUser_FailedLogin() {
        // Arrange: Mock the login method to return null when provided with incorrect credentials
        when(userRepository.login("testUser", "wrongPassword")).thenReturn(null);

        // Act: Call the loginUser method
        boolean result = userService.loginUser("testUser", "wrongPassword");

        // Assert: Verify that the login fails
        assertFalse(result);
    }

    @Test
    void testIsEmailRegistered_True() {
        // Arrange: Mock the isEmailRegistered method to return true for the given email
        when(userRepository.isEmailRegistered("test@example.com")).thenReturn(true);

        // Act: Call the isEmailRegistered method
        boolean result = userService.isEmailRegistered("test@example.com");

        // Assert: Verify that the result is true
        assertTrue(result);
    }

    @Test
    void testIsEmailRegistered_False() {
        // Arrange: Mock the isEmailRegistered method to return false for a different email
        when(userRepository.isEmailRegistered("nonexistent@example.com")).thenReturn(false);

        // Act: Call the isEmailRegistered method
        boolean result = userService.isEmailRegistered("nonexistent@example.com");

        // Assert: Verify that the result is false
        assertFalse(result);
    }
}

