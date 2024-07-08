package com.nuwaish.crm_system_backend_springboot.salesPipeline;


import com.nuwaish.crm_system_backend_springboot.customer.Customer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/sales-pipelines")
public class SalesPipelineController {

    @Autowired
    private SalesPipelineService salesPipelineService;

    @GetMapping
    public ResponseEntity<List<SalesPipeline>> getAllSalesPipelines() {
        List<SalesPipeline> salesPipelines = salesPipelineService.getAllSalesPipelines();
        return ResponseEntity.ok(salesPipelines);
    }

    @GetMapping("/{id}")
    public ResponseEntity<SalesPipeline> getSalesPipeLineById(@PathVariable String id) {
        Optional<SalesPipeline> salesPipeline = salesPipelineService.getSalesPipelineById(id);
        return salesPipeline.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
    }

    @GetMapping("/customer/{customerId}")
    public ResponseEntity<List<SalesPipeline>> getSalesPipelinesByCustomerId(@PathVariable String customerId) {
        List<SalesPipeline> salesPipelines = salesPipelineService.getSalesPipelinesByCustomerId(customerId);
        return  ResponseEntity.ok(salesPipelines);
    }

    @PostMapping("/{customerId}/sales-pipelines")
    public ResponseEntity<Customer> addSalesPipeline(@PathVariable String customerId, @RequestBody SalesPipeline pipeline) {
        Customer customer = salesPipelineService.addSalesPipeline(customerId, pipeline);
        return customer != null ? ResponseEntity.ok(customer) : ResponseEntity.notFound().build();
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteSalesPipelineById(@PathVariable String id) {
        salesPipelineService.deleteSalesPipelineById(id);
        return ResponseEntity.noContent().build();
    }

    @DeleteMapping("/customer/{customerId}")
    public ResponseEntity<Void> deleteSalesPipelinesByCustomerId(@PathVariable String customerId) {
        salesPipelineService.deleteSalesPipelinesByCustomerId(customerId);
        return ResponseEntity.noContent().build();
    }
}
