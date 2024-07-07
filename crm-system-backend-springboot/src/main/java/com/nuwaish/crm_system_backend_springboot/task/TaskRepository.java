package com.nuwaish.crm_system_backend_springboot.task;

import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.List;

public interface TaskRepository extends MongoRepository<Task, String> {
    List<Task> findByCustomerId(String customerId);
}
