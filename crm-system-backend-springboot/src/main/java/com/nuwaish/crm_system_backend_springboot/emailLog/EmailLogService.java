package com.nuwaish.crm_system_backend_springboot.emailLog;

import com.nuwaish.crm_system_backend_springboot.customer.Customer;
import com.nuwaish.crm_system_backend_springboot.customer.CustomerRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class EmailLogService {

    private static final Logger logger = LoggerFactory.getLogger(EmailLogService.class);

    @Autowired
    private EmailLogRepository emailLogRepository;

    @Autowired
    private CustomerRepository customerRepository;

    public List<EmailLog> getAllEmailLogs() {
        return emailLogRepository.findAll();
    }

    public Optional<EmailLog> getEmailLogById(String id) {
        return emailLogRepository.findById(id);
    }

    public List<EmailLog> getEmailLogsByCustomerId(String customerId) {
        return emailLogRepository.findByCustomerId(customerId);
    }

    public EmailLog addEmailLog(String customerId, EmailLog emailLog) {
        emailLog.setCustomerId(customerId);
        EmailLog savedEmailLog = emailLogRepository.save(emailLog);

        Customer customer = customerRepository.findById(customerId).orElse(null);
        if (customer != null) {
            customer.getEmailLogs().add(savedEmailLog);
            customerRepository.save(customer);
        }
        return savedEmailLog;
    }

    public void deleteEmailLogById(String id) {
        emailLogRepository.deleteById(id);
    }

    public void deleteEmailLogsByCustomerId(String customerId) {
        List<EmailLog> emailLogs = emailLogRepository.findByCustomerId(customerId);
        emailLogRepository.deleteAll(emailLogs);
    }
}
