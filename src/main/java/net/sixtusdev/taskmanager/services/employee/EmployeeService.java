package net.sixtusdev.taskmanager.services.employee;

import java.util.List;

import net.sixtusdev.taskmanager.dto.TaskDTO;

public interface EmployeeService {

    List<TaskDTO> getTasksByUserId();

    TaskDTO updateTask(Long id, String status);

}
