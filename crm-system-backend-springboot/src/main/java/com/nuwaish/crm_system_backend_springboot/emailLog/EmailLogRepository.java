package com.nuwaish.crm_system_backend_springboot.emailLog;

import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.List;

public interface EmailLogRepository extends MongoRepository<EmailLog, String> {
    List<EmailLog> findByCustomerId(String customerId);
}
