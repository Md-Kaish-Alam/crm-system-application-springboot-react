package com.nuwaish.crm_system_backend_springboot.salesPipeline;

import com.nuwaish.crm_system_backend_springboot.customer.Customer;
import com.nuwaish.crm_system_backend_springboot.customer.CustomerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class SalesPipelineService {

    @Autowired
    private SalesPipelineRepository salesPipelineRepository;

    @Autowired
    private CustomerRepository customerRepository;

    public List<SalesPipeline> getAllSalesPipelines() {
        return salesPipelineRepository.findAll();
    }

    public Optional<SalesPipeline> getSalesPipelineById(String id) {
        return salesPipelineRepository.findById(id);
    }

    public List<SalesPipeline> getSalesPipelinesByCustomerId(String customerId) {
        return salesPipelineRepository.findByCustomerId(customerId);
    }

    public Customer addSalesPipeline(String customerId, SalesPipeline pipeline) {
        Customer customer = customerRepository.findById(customerId).orElse(null);
        if (customer != null) {
            customer.getSalesPipelines().add(pipeline);
            return customerRepository.save(customer);
        }
        return null;
    }

    public void deleteSalesPipelineById(String id) {
        salesPipelineRepository.deleteById(id);
    }

    public void deleteSalesPipelinesByCustomerId(String customerId) {
        List<SalesPipeline> salesPipelines = salesPipelineRepository.findByCustomerId(customerId);
        salesPipelineRepository.deleteAll(salesPipelines);
    }
}
