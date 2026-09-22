package com.techBloggingApp.Backend.Config;

import com.techBloggingApp.Backend.Entity.Role;
import com.techBloggingApp.Backend.Enum.ERole;
import com.techBloggingApp.Backend.Repository.RoleRepository;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Slf4j
@Component
@AllArgsConstructor
public class DataLoader implements CommandLineRunner {
    private final RoleRepository roleRepository;

    @Override
    public void run(String... args) throws Exception {
        log.info("Initialization of role creation in database");

        for(ERole eRole: ERole.values()) {
            Optional<Role> role = roleRepository.findByRole(eRole);

            if(role.isEmpty()) {
                Role newRole = new Role(eRole);

                log.info("Creation of the role {}", eRole);

                Role savedRole = roleRepository.save(newRole);

                log.info("Role {} created successfully with (ID {}).", eRole, savedRole.getId());
            } else { log.debug("Role {} already exist.", eRole); }

            log.info("Roles initialization finished");
        }
    }
}
