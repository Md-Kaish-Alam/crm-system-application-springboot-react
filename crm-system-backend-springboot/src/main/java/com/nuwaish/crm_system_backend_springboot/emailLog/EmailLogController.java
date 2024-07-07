package com.nuwaish.crm_system_backend_springboot.emailLog;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.swing.plaf.IconUIResource;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/email-logs")
public class EmailLogController {

    @Autowired
    private EmailLogService emailLogService;

    @GetMapping
    public ResponseEntity<List<EmailLog>> getAllEmailLogs() {
        List<EmailLog> emailLogs = emailLogService.getAllEmailLogs();
        return ResponseEntity.ok(emailLogs);
    }

    @GetMapping("/{id}")
    public ResponseEntity<EmailLog> getEmailLogById(@PathVariable String id) {
        Optional<EmailLog> emailLog = emailLogService.getEmailLogById(id);
        return emailLog.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
    }

    @GetMapping("/customer/{customerId}")
    public ResponseEntity<List<EmailLog>> getEmailLogsByCustomerId(@PathVariable String customerId) {
        List<EmailLog> emailLogs = emailLogService.getEmailLogsByCustomerId(customerId);
        return ResponseEntity.ok(emailLogs);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteEmailLogById(@PathVariable String id) {
        emailLogService.deleteEmailLogById(id);
        return ResponseEntity.noContent().build();
    }

    @DeleteMapping("/customer/{customerId}")
    public ResponseEntity<Void> deleteEmailLogsByCustomerId(@PathVariable String customerId) {
        emailLogService.deleteEmailLogsByCustomerId(customerId);
        return ResponseEntity.noContent().build();
    }
}
