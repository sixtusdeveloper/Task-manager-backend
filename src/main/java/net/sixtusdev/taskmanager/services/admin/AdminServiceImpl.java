package net.sixtusdev.taskmanager.services.admin;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;
import lombok.RequiredArgsConstructor;
import net.sixtusdev.taskmanager.dto.TaskDTO;
import net.sixtusdev.taskmanager.entities.Task;
import net.sixtusdev.taskmanager.repositories.TaskRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;

import java.util.Optional;
import java.util.Comparator;
import java.util.Date;

import net.sixtusdev.taskmanager.dto.UserDto;

import net.sixtusdev.taskmanager.entities.User;
import net.sixtusdev.taskmanager.enums.TaskStatus;
import net.sixtusdev.taskmanager.enums.UserRole;
import net.sixtusdev.taskmanager.repositories.UserRepositories;

@Service
@RequiredArgsConstructor
public class AdminServiceImpl implements AdminService {

    private final TaskRepository taskRepository;
    private final UserRepositories userRepositories;

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
            task.setDueDate(Optional.ofNullable(taskDTO.getDueDate()).orElse(new Date())); // Or any default value
            // task.setDueDate(taskDTO.getDueDate()); //Orininal code
            task.setTaskStatus(TaskStatus.INPROGRESS);
            task.setUser(optionalUser.get());
            return taskRepository.save(task).getTaskDTO();

        }
        return null;
    }

    @Override
    public List<TaskDTO> getAllTasks() {

        return taskRepository.findAll().stream()
                // .sorted(Comparator.comparing(Task::getDueDate).reversed())
                .sorted(Comparator.comparing(Task::getDueDate,
                        Comparator.nullsLast(Comparator.naturalOrder()))
                        .reversed())
                .map(Task::getTaskDTO).collect(Collectors.toList());
    }

    @Override
    public Page<Task> getPaginatedTasks(int page, int size) {
        Pageable pageable = PageRequest.of(page, size);
        return taskRepository.findAll(pageable);
    }

    @Override
    public void deleteTask(Long id) {
        taskRepository.deleteById(id);
    }

    @Override
    public TaskDTO getTaskById(Long id) {
        Optional<Task> optionalTask = taskRepository.findById(id);
        if (optionalTask.isPresent()) {
            return optionalTask.map(Task::getTaskDTO).orElse(null);
        }
        return null;
    }

    @Override
    public TaskDTO updateTask(Long id, TaskDTO taskDTO) {
        Optional<Task> optionalTask = taskRepository.findById(id);
        if (optionalTask.isPresent()) {
            Task existingTask = optionalTask.get();
            existingTask.setTitle(taskDTO.getTitle());
            existingTask.setDescription(taskDTO.getDescription());
            existingTask.setPriority(taskDTO.getPriority());
            existingTask.setDueDate(taskDTO.getDueDate());
            existingTask.setTaskStatus(mapStringToTaskStatus(String.valueOf(taskDTO.getTaskStatus())));
            return taskRepository.save(existingTask).getTaskDTO();
        }
        return null;
    }

    private TaskStatus mapStringToTaskStatus(String status) {
        return switch (status) {
            case "PENDING" -> TaskStatus.PENDING;
            case "INPROGRESS" -> TaskStatus.INPROGRESS;
            case "COMPLETED" -> TaskStatus.COMPLETED;
            case "DEFERRED" -> TaskStatus.DEFERRED;
            default -> TaskStatus.CANCELLED;
        };
    }
}

// Original code without the pagination

// package net.sixtusdev.taskmanager.services.admin;

// import java.util.Optional;
// import java.util.Comparator;
// import java.util.Date;
// import java.util.List;
// import java.util.stream.Collectors;

// import org.springframework.stereotype.Service;

// import lombok.RequiredArgsConstructor;
// import net.sixtusdev.taskmanager.dto.TaskDTO;
// import net.sixtusdev.taskmanager.dto.UserDto;
// import net.sixtusdev.taskmanager.entities.Task;
// import net.sixtusdev.taskmanager.entities.User;
// import net.sixtusdev.taskmanager.enums.TaskStatus;
// import net.sixtusdev.taskmanager.enums.UserRole;
// import net.sixtusdev.taskmanager.repositories.UserRepositories;
// import net.sixtusdev.taskmanager.repositories.TaskRepository;

// @Service
// @RequiredArgsConstructor

// public class AdminServiceImpl implements AdminService {

// private final UserRepositories userRepositories;

// private final TaskRepository taskRepository;

// @Override
// public List<UserDto> getUsers() {
// return userRepositories.findAll().stream().filter(user -> user.getUserRole()
// == UserRole.EMPLOYEE)
// .map(User::getUserDto).collect(Collectors.toList());

// }

// @Override
// public TaskDTO createTask(TaskDTO taskDTO) {
// Optional<User> optionalUser =
// userRepositories.findById(taskDTO.getEmployeeId());

// if (optionalUser.isPresent()) {
// Task task = new Task();
// task.setTitle(taskDTO.getTitle());
// task.setDescription(taskDTO.getDescription());
// task.setPriority(taskDTO.getPriority());
// task.setDueDate(Optional.ofNullable(taskDTO.getDueDate()).orElse(new
// Date())); // Or any default value
// // task.setDueDate(taskDTO.getDueDate()); //Orininal code
// task.setTaskStatus(TaskStatus.INPROGRESS);
// task.setUser(optionalUser.get());
// return taskRepository.save(task).getTaskDTO();

// }
// return null;
// }

// @Override
// public List<TaskDTO> getAllTasks() {

// return taskRepository.findAll().stream()
// // .sorted(Comparator.comparing(Task::getDueDate).reversed())
// .sorted(Comparator.comparing(Task::getDueDate,
// Comparator.nullsLast(Comparator.naturalOrder()))
// .reversed())
// .map(Task::getTaskDTO).collect(Collectors.toList());
// }

// }
