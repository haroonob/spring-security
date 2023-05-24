package com.haroonob.spring.security.springsecurity;

import com.haroonob.spring.security.springsecurity.model.Role;
import com.haroonob.spring.security.springsecurity.model.User;
import com.haroonob.spring.security.springsecurity.repository.RoleRepository;
import com.haroonob.spring.security.springsecurity.repository.UserRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

@SpringBootApplication
public class SpringSecurityApplication {

    public static void main(String[] args) {
        SpringApplication.run(SpringSecurityApplication.class, args);
    }

    @Bean
    CommandLineRunner commandLineRunner(UserRepository users, RoleRepository rolesRepo, PasswordEncoder encoder) {
        return args -> {
            List<Role> roles = this.getSampleRoles();
            for (Role role : roles) {
                rolesRepo.save(role);
            }
            users.save(new User("haroonob@gmail.com", encoder.encode("password"), true, "Haroon", "Idrees", null));
            users.save(new User("admin@gmail.com", encoder.encode("admin"), true, "admin", "admin", roles));
        };
    }

    private static List<Role> getSampleRoles() {
        List<String> roleNames = Arrays.asList("ROLE_ADMIN");

        return roleNames.stream()
                .map(Role::new)
                .collect(Collectors.toList());
    }

}
