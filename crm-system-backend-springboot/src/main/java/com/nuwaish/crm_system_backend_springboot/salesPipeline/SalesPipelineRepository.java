package com.nuwaish.crm_system_backend_springboot.salesPipeline;

import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.List;

public interface SalesPipelineRepository extends MongoRepository<SalesPipeline, String> {
    List<SalesPipeline> findByCustomerId(String customerId);
}
