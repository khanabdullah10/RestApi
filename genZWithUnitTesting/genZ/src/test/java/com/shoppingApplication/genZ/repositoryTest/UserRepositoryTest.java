package com.shoppingApplication.genZ.repositoryTest;

import static org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.*;

import com.shoppingApplication.genZ.model.User;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;

import java.util.Arrays;
import java.util.List;

@ExtendWith(MockitoExtension.class)
public class UserRepositoryTest {

    @Mock
    private JdbcTemplate jdbcTemplate;

    @InjectMocks
    private UserRepository userRepository;

    private User user;

    @BeforeEach
    public void setUp() {
        user = new User();
        user.setUsername("testUser");
        user.setPassword("testPassword123");
        user.setEmail("test@example.com");
        user.setRole("USER");
    }

    @Test
    public void testRegisterUser() {
        // Arrange
        String sql = "INSERT INTO User (username, password, email, role) VALUES (?, ?, ?, ?)";
        when(jdbcTemplate.update(eq(sql), eq(user.getUsername()), eq(user.getPassword()), eq(user.getEmail()), eq(user.getRole())))
                .thenReturn(1); // Mock the update method to return 1 (indicating one row was affected)

        // Act
        userRepository.registerUser(user);

        // Assert
        verify(jdbcTemplate, times(1)).update(eq(sql), eq(user.getUsername()), eq(user.getPassword()), eq(user.getEmail()), eq(user.getRole()));
    }

    @Test
    public void testFindUserByEmail() {
        // Arrange
        String email = "test@example.com";
        String sql = "SELECT * FROM User WHERE email = ?";
        when(jdbcTemplate.queryForObject(eq(sql), any(BeanPropertyRowMapper.class), eq(email)))
                .thenReturn(user);

        // Act
        User result = userRepository.findUserByEmail(email);

        // Assert
        assertNotNull(result);
        assertEquals(user.getEmail(), result.getEmail());
        verify(jdbcTemplate, times(1)).queryForObject(eq(sql), any(BeanPropertyRowMapper.class), eq(email));
    }

    @Test
    public void testFindAll() {
        // Arrange
        String sql = "SELECT * FROM User";
        List<User> users = Arrays.asList(user);
        when(jdbcTemplate.query(eq(sql), any(BeanPropertyRowMapper.class)))
                .thenReturn(users);

        // Act
        List<User> result = userRepository.findAll();

        // Assert
        assertNotNull(result);
        assertEquals(1, result.size());
        verify(jdbcTemplate, times(1)).query(eq(sql), any(BeanPropertyRowMapper.class));
    }

    @Test
    public void testLogin() {
        // Arrange
        String username = "testUser";
        String password = "testPassword123";
        String sql = "SELECT * FROM User WHERE username = ? AND password = ?";
        when(jdbcTemplate.queryForObject(eq(sql), any(BeanPropertyRowMapper.class), eq(username), eq(password)))
                .thenReturn(user);

        // Act
        User result = userRepository.login(username, password);

        // Assert
        assertNotNull(result);
        assertEquals(user.getUsername(), result.getUsername());
        verify(jdbcTemplate, times(1)).queryForObject(eq(sql), any(BeanPropertyRowMapper.class), eq(username), eq(password));
    }

    @Test
    public void testLoginUserNotFound() {
        // Arrange
        String username = "testUser";
        String password = "wrongPassword";
        String sql = "SELECT * FROM User WHERE username = ? AND password = ?";
        when(jdbcTemplate.queryForObject(eq(sql), any(BeanPropertyRowMapper.class), eq(username), eq(password)))
                .thenThrow(new RuntimeException("User not found"));

        // Act
        User result = userRepository.login(username, password);

        // Assert
        assertNull(result);
        verify(jdbcTemplate, times(1)).queryForObject(eq(sql), any(BeanPropertyRowMapper.class), eq(username), eq(password));
    }

    @Test
    public void testIsEmailRegistered() {
        // Arrange
        String email = "test@example.com";
        String query = "SELECT COUNT(*) FROM User WHERE email = ?";
        when(jdbcTemplate.queryForObject(eq(query), eq(Integer.class), eq(email)))
                .thenReturn(1);

        // Act
        boolean isRegistered = userRepository.isEmailRegistered(email);

        // Assert
        assertTrue(isRegistered);
        verify(jdbcTemplate, times(1)).queryForObject(eq(query), eq(Integer.class), eq(email));
    }

    @Test
    public void testIsEmailNotRegistered() {
        // Arrange
        String email = "nonexistent@example.com";
        String query = "SELECT COUNT(*) FROM User WHERE email = ?";
        when(jdbcTemplate.queryForObject(eq(query), eq(Integer.class), eq(email)))
                .thenReturn(0);

        // Act
        boolean isRegistered = userRepository.isEmailRegistered(email);

        // Assert
        assertFalse(isRegistered);
        verify(jdbcTemplate, times(1)).queryForObject(eq(query), eq(Integer.class), eq(email));
    }
}
