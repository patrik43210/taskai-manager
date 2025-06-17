package com.patrik.taskai.taskai_manager;

import com.patrik.taskai.taskai_manager.service.JwtService;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class JwtServiceTest {

    private final JwtService jwtService = new JwtService();

    @Test
    void shouldGenerateValidJwtToken(){
        String token = jwtService.generateToken("test@example.com");
        assertNotNull(token);
        assertTrue(token.length() > 20);
    }
}
