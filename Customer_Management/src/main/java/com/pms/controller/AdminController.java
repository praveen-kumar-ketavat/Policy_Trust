package com.pms.controller;

import com.pms.entity.Admin;
import com.pms.entity.Customer;
import com.pms.service.AdminService;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/admin")
public class AdminController {

    @Autowired
    AdminService service;

    @PostMapping("/login")
    public String login(@RequestBody Admin admin) {
        return service.login(admin.getEmail(), admin.getPassword());
    }
    
    @GetMapping("viewAllCustomers")
    public List<Customer> viewAllCustomers(){
    	return service.getVerifiedCustomers();
    }
    
    @GetMapping("viewUnverifiedCustomers")
    public List<Customer> viewUnverifiedCustomers(){
    	return service.getUnverifiedCustomers();
    }
    
    
}
