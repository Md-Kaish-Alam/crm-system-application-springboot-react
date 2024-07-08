package com.nuwaish.crm_system_backend_springboot.customer;

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

}
