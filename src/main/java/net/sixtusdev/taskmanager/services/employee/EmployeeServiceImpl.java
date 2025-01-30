package net.sixtusdev.taskmanager.services.employee;

import java.util.Comparator;
import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import net.sixtusdev.taskmanager.entities.Comment;
import net.sixtusdev.taskmanager.entities.Task;
import net.sixtusdev.taskmanager.entities.User;
import net.sixtusdev.taskmanager.enums.TaskStatus;

import org.springframework.stereotype.Service;

import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import net.sixtusdev.taskmanager.dto.CommentDTO;
import net.sixtusdev.taskmanager.dto.TaskDTO;
import net.sixtusdev.taskmanager.repositories.TaskRepository;
import net.sixtusdev.taskmanager.repositories.CommentRepository;

import net.sixtusdev.taskmanager.utils.JwtUtils;

@Service
@RequiredArgsConstructor
public class EmployeeServiceImpl implements EmployeeService {

    private final TaskRepository taskRepository;

    private final JwtUtils jwtUtils;

    private final CommentRepository commentRepository;

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

    @Override
    public TaskDTO updateTask(Long id, String status) {
        Optional<Task> taskOptional = taskRepository.findById(id);
        if (taskOptional.isPresent()) {
            Task existingTask = taskOptional.get();
            existingTask.setTaskStatus(mapStringToTaskStatus(status));
            return taskRepository.save(existingTask).getTaskDTO();

        }
        throw new EntityNotFoundException("Task not found");
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

    @Override
    public TaskDTO getTaskById(Long id) {
        Optional<Task> taskOptional = taskRepository.findById(id);
        if (taskOptional.isPresent()) {
            return taskOptional.get().getTaskDTO();
        }
        throw new EntityNotFoundException("Task not found");
    }

    @Override
    public CommentDTO createComment(Long taskId, String content) {
        Optional<Task> optionalTask = taskRepository.findById(taskId);
        User user = jwtUtils.getLoggedInUser();
        if (optionalTask.isPresent() && user != null) {
            Comment comment = new Comment();
            comment.setCreatedAt(new Date());
            comment.setContent(content);
            comment.setTask(optionalTask.get());
            comment.setUser(user);
            return commentRepository.save(comment).getCommentDTO();
        }
        throw new EntityNotFoundException("User or Task not found");

    }

    @Override
    public List<CommentDTO> getCommentByTaskId(Long taskId) {
        return commentRepository.findAllByTaskId(taskId).stream()
                .map(Comment::getCommentDTO).collect(Collectors.toList());
    }

}
