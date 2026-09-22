package com.techBloggingApp.Backend.Repository;

import com.techBloggingApp.Backend.Entity.Role;
import com.techBloggingApp.Backend.Enum.ERole;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface RoleRepository extends JpaRepository<Role, Long> {
    Optional<Role> findByRole(ERole role);
}
