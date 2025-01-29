package net.sixtusdev.taskmanager.services.admin;

import java.util.List;

import org.springframework.data.domain.Page;

import net.sixtusdev.taskmanager.dto.TaskDTO;
import net.sixtusdev.taskmanager.dto.UserDto;
import net.sixtusdev.taskmanager.entities.Task;

public interface AdminService {

    List<UserDto> getUsers();

    TaskDTO createTask(TaskDTO taskDTO);

    List<TaskDTO> getAllTasks();

    Page<Task> getPaginatedTasks(int page, int size);

}
