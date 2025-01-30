package net.sixtusdev.taskmanager.services.employee;

import java.util.List;

import net.sixtusdev.taskmanager.dto.CommentDTO;
import net.sixtusdev.taskmanager.dto.TaskDTO;

public interface EmployeeService {

    List<TaskDTO> getTasksByUserId();

    TaskDTO updateTask(Long id, String status);

    TaskDTO getTaskById(Long id);

    CommentDTO createComment(Long taskId, String content);

    List<CommentDTO> getCommentByTaskId(Long taskId);

}
