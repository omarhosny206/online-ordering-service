package com.example.config;

import com.example.entity.Role;
import com.example.repository.RoleRepository;
import com.example.util.Roles;
import org.modelmapper.ModelMapper;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.Arrays;
import java.util.List;

@Configuration
public class BeanConfig {
    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder(10);
    }

    @Bean
    public ModelMapper modelMapper() {
        return new ModelMapper();
    }

    @Bean
    public CommandLineRunner seedRoles(RoleRepository roleRepository) {
        return (args) -> {
            List<String> rolesNames = Arrays.stream(Roles.values()).map(roles -> roles.getValue()).toList();
            rolesNames.forEach(roleName -> {
                Role storedRole = roleRepository.findByName(roleName);
                if (storedRole == null) {
                    roleRepository.save(new Role(roleName));
                }
            });
        };
    }
}
