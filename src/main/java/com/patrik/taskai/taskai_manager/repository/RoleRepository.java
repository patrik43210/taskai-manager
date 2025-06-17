package com.patrik.taskai.taskai_manager.repository;

import com.patrik.taskai.taskai_manager.model.Role;
import com.patrik.taskai.taskai_manager.model.RoleName;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface RoleRepository extends JpaRepository<Role, Long> {
    Optional<Role> findByName(RoleName name);
}
