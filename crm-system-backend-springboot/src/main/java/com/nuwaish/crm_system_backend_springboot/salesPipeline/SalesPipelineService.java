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

    public SalesPipeline addSalesPipeline(String customerId, SalesPipeline salesPipeline) {
        salesPipeline.setCustomerId(customerId);
        SalesPipeline savedSalesPipeline = salesPipelineRepository.save(salesPipeline);
        Customer customer = customerRepository.findById(customerId).orElse(null);
        if (customer != null) {
            customer.getSalesPipelines().add(savedSalesPipeline);
            customerRepository.save(customer);
        }
        return savedSalesPipeline;
    }

    public SalesPipeline updateSalesPipeline(String id, SalesPipeline updatedSalesPipeline) {
        Optional<SalesPipeline> existingPipelineOptional = salesPipelineRepository.findById(id);
        if (existingPipelineOptional.isPresent()) {
            SalesPipeline existingPipeline = existingPipelineOptional.get();
            existingPipeline.setStage(updatedSalesPipeline.getStage());
            existingPipeline.setDescription(updatedSalesPipeline.getDescription());

            return salesPipelineRepository.save(existingPipeline);
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
