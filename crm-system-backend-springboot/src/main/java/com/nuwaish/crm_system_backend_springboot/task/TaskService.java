package com.nuwaish.crm_system_backend_springboot.task;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class TaskService {

    @Autowired
    private TaskRepository taskRepository;

    public List<Task> getAllTasks() {
        return taskRepository.findAll();
    }

    public Optional<Task> getTasksById(String id) {
        return taskRepository.findById(id);
    }

    public List<Task> getTasksByCustomerId(String customerId) {
        return taskRepository.findByCustomerId(customerId);
    }

    public void deleteTaskById(String id) {
        taskRepository.deleteById(id);
    }

    public void deleteTasksByCustomerId(String customerId) {
        List<Task> tasks = taskRepository.findByCustomerId(customerId);
        taskRepository.deleteAll(tasks);
    }

    public Task updateTaskById(String id, Task updatedTask) {
        Optional<Task> taskOptional = taskRepository.findById(id);

        if (taskOptional.isPresent()) {
            Task task = taskOptional.get();

            task.setTitle(updatedTask.getTitle());
            task.setDescription(updatedTask.getDescription());
            task.setDueDate(updatedTask.getDueDate());
            task.setCompleted(updatedTask.isCompleted());

            return taskRepository.save(task);
        }

        return null;
    }
}
