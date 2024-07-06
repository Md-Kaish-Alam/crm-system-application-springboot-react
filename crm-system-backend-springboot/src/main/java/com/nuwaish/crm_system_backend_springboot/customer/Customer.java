package com.nuwaish.crm_system_backend_springboot.customer;


import com.nuwaish.crm_system_backend_springboot.Task.Task;
import com.nuwaish.crm_system_backend_springboot.emailLog.EmailLog;
import com.nuwaish.crm_system_backend_springboot.salesPipeline.SalesPipeline;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.List;

@Document(collection = "customers")
public class Customer {

    @Id
    private String id;
    private String name;
    private String email;
    private String mobile;
    private String address;

    @DBRef
    private List<SalesPipeline> salesPipelines;

    @DBRef
    private List<EmailLog> emailLogs;

    @DBRef
    private List<Task> tasks;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public List<SalesPipeline> getSalesPipelines() {
        return salesPipelines;
    }

    public void setSalesPipelines(List<SalesPipeline> salesPipelines) {
        this.salesPipelines = salesPipelines;
    }

    public List<EmailLog> getEmailLogs() {
        return emailLogs;
    }

    public void setEmailLogs(List<EmailLog> emailLogs) {
        this.emailLogs = emailLogs;
    }

    public List<Task> getTasks() {
        return tasks;
    }

    public void setTasks(List<Task> tasks) {
        this.tasks = tasks;
    }
}
