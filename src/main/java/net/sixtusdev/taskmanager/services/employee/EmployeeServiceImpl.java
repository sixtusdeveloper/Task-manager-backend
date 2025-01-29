package net.sixtusdev.taskmanager.services.employee;

import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

import net.sixtusdev.taskmanager.entities.Task;
import net.sixtusdev.taskmanager.entities.User;
import org.springframework.stereotype.Service;

import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import net.sixtusdev.taskmanager.dto.TaskDTO;
import net.sixtusdev.taskmanager.repositories.TaskRepository;

import net.sixtusdev.taskmanager.utils.JwtUtils;

@Service
@RequiredArgsConstructor
public class EmployeeServiceImpl implements EmployeeService {

    private final TaskRepository taskRepository;

    private final JwtUtils jwtUtils;

    @Override
    public List<TaskDTO> getTasksByUserId() {

        User user = jwtUtils.getLoggedInUser();

        if (user != null) {
            return taskRepository.findAllByUserId(user.getId()).stream()
                    .sorted(Comparator.comparing(Task::getDueDate).reversed())
                    .map(Task::getTaskDTO).collect(Collectors.toList());
        }
        throw new EntityNotFoundException("User not found");
    }

}
