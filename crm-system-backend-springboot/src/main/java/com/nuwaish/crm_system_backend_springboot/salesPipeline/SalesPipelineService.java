package com.nuwaish.crm_system_backend_springboot.salesPipeline;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class SalesPipelineService {

    @Autowired
    private SalesPipelineRepository salesPipelineRepository;

    public List<SalesPipeline> getAllSalesPipelines() {
        return salesPipelineRepository.findAll();
    }

    public Optional<SalesPipeline> getSalesPipelineById(String id) {
        return salesPipelineRepository.findById(id);
    }

    public List<SalesPipeline> getSalesPipelinesByCustomerId(String customerId) {
        return salesPipelineRepository.findByCustomerId(customerId);
    }

    public void deleteSalesPipelineById(String id) {
        salesPipelineRepository.deleteById(id);
    }

    public void deleteSalesPipelinesByCustomerId(String customerId) {
        List<SalesPipeline> salesPipelines = salesPipelineRepository.findByCustomerId(customerId);
        salesPipelineRepository.deleteAll(salesPipelines);
    }
}
