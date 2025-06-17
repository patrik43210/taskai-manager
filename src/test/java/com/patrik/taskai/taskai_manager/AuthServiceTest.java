package com.patrik.taskai.taskai_manager;

import com.patrik.taskai.taskai_manager.dto.LoginRequest;
import com.patrik.taskai.taskai_manager.dto.RegisterRequest;
import com.patrik.taskai.taskai_manager.model.Role;
import com.patrik.taskai.taskai_manager.model.RoleName;
import com.patrik.taskai.taskai_manager.model.User;
import com.patrik.taskai.taskai_manager.repository.UserRepository;
import com.patrik.taskai.taskai_manager.service.AuthService;
import com.patrik.taskai.taskai_manager.service.JwtService;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class AuthServiceTest {

    @Mock
    private UserRepository userRepository;

    @Mock
    private PasswordEncoder passwordEncoder;

    @Mock
    private JwtService jwtService;

    @InjectMocks
    private AuthService authService;

    public AuthServiceTest() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void shouldRegisterNewUser() {
        RegisterRequest request = new RegisterRequest("test@example.com", "password123");

        when(userRepository.findByEmail("test@example.com")).thenReturn(Optional.empty());
        when(passwordEncoder.encode("password123")).thenReturn("hashedPass");

        String result = authService.register(request);

        assertEquals("User registered successfully", result);
        verify(userRepository, times(1)).save(any(User.class));
    }


    @Test
    void shouldLoginAndReturnJwtToken() {
        LoginRequest req = new LoginRequest("test@example.com", "password123");
        User user = new User(10L, "test@example.com", "hashedPass", new Role(1L, RoleName.USER));

        when(userRepository.findByEmail("test@example.com")).thenReturn(Optional.of(user));
        when(passwordEncoder.matches("password123", "hashedPass")).thenReturn(true);
        when(jwtService.generateToken("test@example.com")).thenReturn("jwt-token");

        String token = authService.login(req);

        assertEquals("jwt-token", token);
    }
}
