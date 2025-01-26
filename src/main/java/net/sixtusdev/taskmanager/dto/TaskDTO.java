package net.sixtusdev.taskmanager.dto;

import java.util.Date;

import lombok.Data;
import net.sixtusdev.taskmanager.enums.TaskStatus;

@Data

public class TaskDTO {

    private Long id;
    private String title;
    private String description;
    private String priority;
    private Date dueDate;
    private TaskStatus taskStatus;

    private Long employeeId;

    private String employeeName;

}
