package net.sixtusdev.taskmanager.services.admin;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;

import lombok.RequiredArgsConstructor;
import net.sixtusdev.taskmanager.dto.UserDto;
import net.sixtusdev.taskmanager.entities.User;
import net.sixtusdev.taskmanager.enums.UserRole;
import net.sixtusdev.taskmanager.repositories.UserRepositories;

@Service
@RequiredArgsConstructor

public class AdminServiceImpl implements AdminService {

    private final UserRepositories userRepositories;

    @Override
    public List<UserDto> getUsers() {
        return userRepositories.findAll().stream().filter(user -> user.getUserRole() == UserRole.EMPLOYEE)
                .map(User::getUserDto).collect(Collectors.toList());

    }

}
