package com.patrik.taskai.taskai_manager.config;

import com.patrik.taskai.taskai_manager.model.Role;
import com.patrik.taskai.taskai_manager.model.RoleName;
import com.patrik.taskai.taskai_manager.repository.RoleRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class DataInitializer implements CommandLineRunner {

    private final RoleRepository roleRepository;

    @Override
    public void run(String... args) {
        for (RoleName roleName : RoleName.values()) {
            roleRepository.findByName(roleName)
                    .orElseGet(() -> roleRepository.save(new Role(null, roleName)));
        }
    }
}
