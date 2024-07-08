package com.nuwaish.crm_system_backend_springboot.task;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/tasks")
public class TaskController {

    @Autowired
    private TaskService taskService;

    @GetMapping
    public ResponseEntity<List<Task>> getAllTasks() {
        List<Task> tasks = taskService.getAllTasks();
        return  ResponseEntity.ok(tasks);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Task> getTaskById(@PathVariable String id) {
        Optional<Task> task = taskService.getTasksById(id);
        return task.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
    }

    @GetMapping("/customer/{customerId}")
    public ResponseEntity<List<Task>> getTasksByCustomerId(@PathVariable String customerId) {
        List<Task> tasks = taskService.getTasksByCustomerId(customerId);
        return ResponseEntity.ok(tasks);
    }

    @PostMapping("/customer/{customerId}")
    public ResponseEntity<Task> addTask(@PathVariable String customerId, @RequestBody Task task) {
        Task savedTask = taskService.addTask(customerId, task);
        return ResponseEntity.ok(savedTask);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Task> updateTask(@PathVariable String id, @RequestBody Task updateTask) {
        Task task = taskService.updateTaskById(id, updateTask);

        if (task != null) {
            return ResponseEntity.ok(task);
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteTaskById(@PathVariable String id) {
        taskService.deleteTaskById(id);
        return ResponseEntity.noContent().build();
    }

    @DeleteMapping("/customer/{customerId}")
    public ResponseEntity<Void> deleteTaskByCustomerId(@PathVariable String customerId) {
        taskService.deleteTasksByCustomerId(customerId);
        return ResponseEntity.noContent().build();
    }
}
