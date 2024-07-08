package com.nuwaish.crm_system_backend_springboot.emailLog;


import com.nuwaish.crm_system_backend_springboot.customer.Customer;
import com.nuwaish.crm_system_backend_springboot.customer.CustomerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class EmailLogService {

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

    public Customer addEmailLog(String customerId, EmailLog emailLog) {
        Customer customer = customerRepository.findById(customerId).orElse(null);
        if (customer != null) {
            customer.getEmailLogs().add(emailLog);
            return customerRepository.save(customer);
        }
        return null;
    }

    public void deleteEmailLogById(String id) {
        emailLogRepository.deleteById(id);
    }

    public void deleteEmailLogsByCustomerId(String customerId) {
        List<EmailLog> emailLogs = emailLogRepository.findByCustomerId(customerId);
        emailLogRepository.deleteAll(emailLogs);
    }
}
