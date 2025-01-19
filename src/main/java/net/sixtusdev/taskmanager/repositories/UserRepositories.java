package net.sixtusdev.taskmanager.repositories;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

// import net.sixtusdev.taskmanager.repositories UserRepositories;
import net.sixtusdev.taskmanager.entities.User;
import net.sixtusdev.taskmanager.enums.UserRole;

@Repository
public interface UserRepositories extends JpaRepository<User, Long> {

    Optional<User> findFirstByEmail(String username);

    Optional<User> findByUserRole(UserRole admin);

}
