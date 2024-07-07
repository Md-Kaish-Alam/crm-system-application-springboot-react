package com.nuwaish.crm_system_backend_springboot.customer;

import com.nuwaish.crm_system_backend_springboot.emailLog.EmailLog;
import com.nuwaish.crm_system_backend_springboot.salesPipeline.SalesPipeline;
import com.nuwaish.crm_system_backend_springboot.task.Task;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;


@Service
public class CustomerService {

    @Autowired
    private CustomerRepository customerRepository;

    public List<Customer> getAllCustomers() {
        return customerRepository.findAll();
    }

    public Customer getCustomerById(String id) {
        return customerRepository.findById(id).orElse(null);
    }

    public Customer createCustomer(Customer customer) {
        return customerRepository.save(customer);
    }

    public Customer updateCustomer(String id, Customer updatedCustomer) {
        Customer existingCustomer = customerRepository.findById(id).orElse(null);
        if (existingCustomer != null) {
            // Update fields
            existingCustomer.setName(updatedCustomer.getName());
            existingCustomer.setMobile(updatedCustomer.getMobile());
            existingCustomer.setAddress(updatedCustomer.getAddress());
            // Save updated customer
            return customerRepository.save(existingCustomer);
        }
        return null;
    }

    public void deleteCustomer(String id) {
        customerRepository.deleteById(id);
    }

    public Customer addSalesPipeline(String customerId, SalesPipeline pipeline) {
        Customer customer = customerRepository.findById(customerId).orElse(null);
        if (customer != null) {
            customer.getSalesPipelines().add(pipeline);
            return customerRepository.save(customer);
        }
        return null;
    }

    public Customer addEmailLog(String customerId, EmailLog emailLog) {
        Customer customer = customerRepository.findById(customerId).orElse(null);
        if (customer != null) {
            customer.getEmailLogs().add(emailLog);
            return customerRepository.save(customer);
        }
        return null;
    }

    public Customer addTask(String customerId, Task task) {
        Customer customer = customerRepository.findById(customerId).orElse(null);
        if (customer != null) {
            customer.getTasks().add(task);
            return customerRepository.save(customer);
        }
        return null;
    }
}
