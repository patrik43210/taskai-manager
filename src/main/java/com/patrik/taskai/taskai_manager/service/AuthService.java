package com.patrik.taskai.taskai_manager.service;

import com.patrik.taskai.taskai_manager.dto.RegisterRequest;
import com.patrik.taskai.taskai_manager.model.Role;
import com.patrik.taskai.taskai_manager.model.User;
import com.patrik.taskai.taskai_manager.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AuthService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    public String register(RegisterRequest request){
        if(userRepository.findByEmail(request.getEmail()).isPresent()){
            throw  new RuntimeException("User already exist");
        }
        User user = User.builder()
                .email(request.getEmail())
                .password(passwordEncoder.encode(request.getPassword()))
                .role(Role.USER)
                .build();

        userRepository.save(user);
        return "User registered successfully";
    }
}
