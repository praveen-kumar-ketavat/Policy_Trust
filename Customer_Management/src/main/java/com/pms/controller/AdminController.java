package com.pms.controller;

import com.pms.entity.Admin;
import com.pms.entity.Customer;
import com.pms.exception.InvalidEntityException;
import com.pms.service.AdminService;
import com.pms.service.CustomerService;
import com.pms.service.EmailService;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/admin")
public class AdminController {

    @Autowired
    AdminService service;
    CustomerService custService;

    @PostMapping("/login")
    public String login(@RequestBody Admin admin) {
        return service.login(admin.getEmail(), admin.getPassword());
    }
    
    @GetMapping("/viewVerifiedCustomers")
    public List<Customer> viewVerifiedCustomers(){
    	return service.getVerifiedCustomers();
    }
    
    @GetMapping("/viewUnverifiedCustomers")
    public List<Customer> viewUnverifiedCustomers(){
    	return service.getUnverifiedCustomers();
    }
    
    @GetMapping("/viewdeletedCustomers")
    public List<Customer> viewdeletedCustomers(){
    	return service.getDeletedCustomers();
    }
    
    
    @PostMapping("/deleteCustomer")
	public String deleteCustomer(@RequestBody Customer cust) throws InvalidEntityException {
    	return service.deleteCustomer(cust);
	}
    
    @PostMapping("/acceptCustomer")
    public String acceptCustomer(@RequestBody Customer cust) throws InvalidEntityException {
    	return service.acceptCustomer(cust);
    }
    
    @PostMapping("/rejectCustomer")
    public String rejectCustomer(@RequestBody Customer cust) throws InvalidEntityException {
    	return service.rejectCustomer(cust);
    }
    
    
}
