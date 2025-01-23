package net.sixtusdev.taskmanager.services.admin;

import java.util.List;

import net.sixtusdev.taskmanager.dto.UserDto;

public interface AdminService {

    List<UserDto> getUsers();

}
