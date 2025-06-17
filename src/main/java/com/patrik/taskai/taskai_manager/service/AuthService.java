package com.patrik.taskai.taskai_manager.service;

import com.patrik.taskai.taskai_manager.dto.LoginRequest;
import com.patrik.taskai.taskai_manager.dto.RegisterRequest;
import com.patrik.taskai.taskai_manager.model.Role;
import com.patrik.taskai.taskai_manager.model.RoleName;
import com.patrik.taskai.taskai_manager.repository.RoleRepository;
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
    private final RoleRepository roleRepository;

    private final JwtService jwtService;

    public String register(RegisterRequest request){
        if(userRepository.findByEmail(request.getEmail()).isPresent()){
            throw  new RuntimeException("User already exist");
        }
        Role userRole = roleRepository.findByName(RoleName.USER)
                .orElseThrow(() -> new RuntimeException("Default role missing"));
        User user = User.builder()
                .email(request.getEmail())
                .password(passwordEncoder.encode(request.getPassword()))
                .role(userRole)
                .build();

        userRepository.save(user);
        return "User registered successfully";
    }

    public String login(LoginRequest request) {
        User user = userRepository.findByEmail(request.getEmail()).orElseThrow(() -> new RuntimeException("User nor found"));

        if(!passwordEncoder.matches(request.getPassword(), user.getPassword())){
            throw  new RuntimeException("Invalid credentials");
        }

        return  jwtService.generateToken(user.getEmail());
    }
}
