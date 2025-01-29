package net.sixtusdev.taskmanager.controller.admin;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import lombok.RequiredArgsConstructor;
import net.sixtusdev.taskmanager.dto.TaskDTO;
import net.sixtusdev.taskmanager.entities.Task;
import net.sixtusdev.taskmanager.services.admin.AdminService;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/admin")
public class AdminController {

    private final AdminService adminService;

    @GetMapping("/users")
    public ResponseEntity<?> getUsers() {
        return ResponseEntity.ok(adminService.getUsers());
    }

    @PostMapping("/task")
    public ResponseEntity<TaskDTO> createTask(@RequestBody TaskDTO taskDTO) {
        TaskDTO createdTaskDTO = adminService.createTask(taskDTO);
        if (createdTaskDTO == null)
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();

        return ResponseEntity.status(HttpStatus.CREATED).body(createdTaskDTO);
    }

    @GetMapping("/tasks")
    public ResponseEntity<?> getPaginatedTasks(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "5") int size) {
        Page<Task> paginatedTasks = adminService.getPaginatedTasks(page, size);
        return ResponseEntity.ok().body(paginatedTasks);
    }

    @DeleteMapping("/task/{id}")
    public ResponseEntity<Void> deleteTask(@PathVariable Long id) {
        adminService.deleteTask(id);
        return ResponseEntity.ok().body(null);

    }

    @GetMapping("/task/{id}")
    public ResponseEntity<TaskDTO> getTaskById(@PathVariable Long id) {
        return ResponseEntity.ok(adminService.getTaskById(id));
    }

    @PutMapping("/task/{id}")
    public ResponseEntity<?> updateTask(@PathVariable Long id, @RequestBody TaskDTO taskDTO) {
        TaskDTO updatedTask = adminService.updateTask(id, taskDTO);
        if (updatedTask == null)
            return ResponseEntity.notFound().build();

        return ResponseEntity.ok(updatedTask);
    }

    @GetMapping("/tasks/search/{title}")
    public ResponseEntity<List<TaskDTO>> searchTasK(@PathVariable String title) {
        return ResponseEntity.ok(adminService.searchTaskByTitle(title));
    }

}

// Original code without pagination

// package net.sixtusdev.taskmanager.controller.admin;

// import org.springframework.http.HttpStatus;
// import org.springframework.http.ResponseEntity;
// import org.springframework.web.bind.annotation.GetMapping;
// import org.springframework.web.bind.annotation.PostMapping;
// import org.springframework.web.bind.annotation.RequestBody;
// import org.springframework.web.bind.annotation.RequestMapping;
// import org.springframework.web.bind.annotation.RestController;

// import lombok.RequiredArgsConstructor;
// import net.sixtusdev.taskmanager.dto.TaskDTO;
// import net.sixtusdev.taskmanager.services.admin.AdminService;

// @RestController
// @RequiredArgsConstructor
// @RequestMapping("/api/admin")
// public class AdminController {
// private final AdminService adminService;

// @GetMapping("/users")
// public ResponseEntity<?> getUsers() {
// return ResponseEntity.ok(adminService.getUsers());
// }

// @PostMapping("/task")
// public ResponseEntity<TaskDTO> createTask(@RequestBody TaskDTO taskDTO) {
// TaskDTO createdTaskDTO = adminService.createTask(taskDTO);
// if (createdTaskDTO == null)
// return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();

// return ResponseEntity.status(HttpStatus.CREATED).body(createdTaskDTO);
// }

// @GetMapping("/tasks")
// public ResponseEntity<?> getAllTasks() {
// return ResponseEntity.ok(adminService.getAllTasks());
// }
// }
