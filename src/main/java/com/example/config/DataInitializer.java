package com.example.config;

import com.example.model.Account;
import com.example.model.Role;
import com.example.repository.AccountRepository;
import com.example.repository.RoleRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.Set;

@Configuration
public class DataInitializer {

    @Bean
    public CommandLineRunner initUsers(RoleRepository roleRepository,
            AccountRepository accountRepository,
            PasswordEncoder passwordEncoder) {
        return args -> {
            Role adminRole = roleRepository.findByName("ADMIN").orElseGet(() -> {
                Role role = new Role();
                role.setName("ADMIN");
                return roleRepository.save(role);
            });

            Role userRole = roleRepository.findByName("USER").orElseGet(() -> {
                Role role = new Role();
                role.setName("USER");
                return roleRepository.save(role);
            });

            createAccountIfMissing(accountRepository, passwordEncoder,
                    "nguyen.van.an", "Nguyen Van An", "an123", Set.of(adminRole, userRole));
            createAccountIfMissing(accountRepository, passwordEncoder,
                    "tran.thi.binh", "Tran Thi Binh", "binh123", Set.of(userRole));
            createAccountIfMissing(accountRepository, passwordEncoder,
                    "le.van.cuong", "Le Van Cuong", "cuong123", Set.of(userRole));
        };
    }

    private void createAccountIfMissing(AccountRepository accountRepository,
            PasswordEncoder passwordEncoder,
            String username,
            String fullName,
            String rawPassword,
            Set<Role> roles) {
        if (accountRepository.findByUsername(username).isPresent()) {
            return;
        }

        Account account = new Account();
        account.setUsername(username);
        account.setFullName(fullName);
        account.setPassword(passwordEncoder.encode(rawPassword));
        account.setEnabled(true);
        account.setRoles(roles);
        accountRepository.save(account);
    }
}
