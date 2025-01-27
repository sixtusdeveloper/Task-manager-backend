package net.sixtusdev.taskmanager.services.admin;

import java.util.Optional;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;

import lombok.RequiredArgsConstructor;
import net.sixtusdev.taskmanager.dto.TaskDTO;
import net.sixtusdev.taskmanager.dto.UserDto;
import net.sixtusdev.taskmanager.entities.Task;
import net.sixtusdev.taskmanager.entities.User;
import net.sixtusdev.taskmanager.enums.TaskStatus;
import net.sixtusdev.taskmanager.enums.UserRole;
import net.sixtusdev.taskmanager.repositories.UserRepositories;
import net.sixtusdev.taskmanager.repositories.TaskRepository;

@Service
@RequiredArgsConstructor

public class AdminServiceImpl implements AdminService {

    private final UserRepositories userRepositories;

    private final TaskRepository taskRepository;

    @Override
    public List<UserDto> getUsers() {
        return userRepositories.findAll().stream().filter(user -> user.getUserRole() == UserRole.EMPLOYEE)
                .map(User::getUserDto).collect(Collectors.toList());

    }

    @Override
    public TaskDTO createTask(TaskDTO taskDTO) {
        Optional<User> optionalUser = userRepositories.findById(taskDTO.getEmployeeId());

        if (optionalUser.isPresent()) {
            Task task = new Task();
            task.setTitle(taskDTO.getTitle());
            task.setDescription(taskDTO.getDescription());
            task.setPriority(taskDTO.getPriority());
            task.setDueDate(taskDTO.getDueDate());
            task.setTaskStatus(TaskStatus.INPROGRESS);
            task.setUser(optionalUser.get());
            return taskRepository.save(task).getTaskDTO();

        }
        return null;
    }

}
