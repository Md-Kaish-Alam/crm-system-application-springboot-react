package com.nuwaish.crm_system_backend_springboot.customer;

import com.nuwaish.crm_system_backend_springboot.emailLog.EmailLog;
import com.nuwaish.crm_system_backend_springboot.salesPipeline.SalesPipeline;
import com.nuwaish.crm_system_backend_springboot.task.Task;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/customers")
public class CustomerController {

    @Autowired
    private CustomerService customerService;

    @GetMapping
    public ResponseEntity<List<Customer>> getAllCustomers() {
        List<Customer> customers = customerService.getAllCustomers();
        return ResponseEntity.ok(customers);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Customer> getCustomerById(@PathVariable String id) {
        Customer customer = customerService.getCustomerById(id);
        return customer != null ? ResponseEntity.ok(customer) : ResponseEntity.notFound().build();
    }

    @PostMapping
    public ResponseEntity<Customer> createCustomer(@RequestBody Customer customer) {
        Customer createdCustomer = customerService.createCustomer(customer);
        return ResponseEntity.status(HttpStatus.CREATED).body(createdCustomer);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Customer> updateCustomer(@PathVariable String id, @RequestBody Customer updatedCustomer) {
        Customer customer = customerService.updateCustomer(id, updatedCustomer);
        return customer != null ? ResponseEntity.ok(customer) : ResponseEntity.notFound().build();
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteCustomer(@PathVariable String id) {
        customerService.deleteCustomer(id);
        return ResponseEntity.noContent().build();
    }

    @PostMapping("/{customerId}/sales-pipelines")
    public ResponseEntity<Customer> addSalesPipeline(@PathVariable String customerId, @RequestBody SalesPipeline pipeline) {
        Customer customer = customerService.addSalesPipeline(customerId, pipeline);
        return customer != null ? ResponseEntity.ok(customer) : ResponseEntity.notFound().build();
    }

    @PostMapping("/{customerId}/email-logs")
    public ResponseEntity<Customer> addEmailLog(@PathVariable String customerId, @RequestBody EmailLog emailLog) {
        Customer customer = customerService.addEmailLog(customerId, emailLog);
        return customer != null ? ResponseEntity.ok(customer) : ResponseEntity.notFound().build();
    }

    @PostMapping("/{customerId}/tasks")
    public ResponseEntity<Customer> addTask(@PathVariable String customerId, @RequestBody Task task) {
        Customer customer = customerService.addTask(customerId, task);
        return customer != null ? ResponseEntity.ok(customer) : ResponseEntity.notFound().build();
    }
}
