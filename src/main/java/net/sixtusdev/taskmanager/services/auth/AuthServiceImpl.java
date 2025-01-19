package net.sixtusdev.taskmanager.services.auth;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import java.util.Optional;

import net.sixtusdev.taskmanager.entities.User;
import net.sixtusdev.taskmanager.enums.UserRole;
import net.sixtusdev.taskmanager.repositories.UserRepositories;

@Service
@RequiredArgsConstructor

public class AuthServiceImpl implements AuthService {

    private final UserRepositories userRepositories;

    @PostConstruct
    public void createAnAdminAccount() {
        Optional<User> optionalUser = userRepositories.findByUserRole(UserRole.ADMIN);

        if (optionalUser.isEmpty()) {
            User user = new User();
            user.setEmail("admin@gmail.com");
            user.setName("admin");
            user.setPassword(new BCryptPasswordEncoder().encode("admin"));
            user.setUserRole(UserRole.ADMIN);
            userRepositories.save(user);
            System.out.println("Admin account created successfully!");
        } else {
            System.out.println("Admin account already exists!");
        }
    }

}
