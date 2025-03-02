package com.pms.controller;

import com.pms.entity.Admin;
import com.pms.entity.Customer;
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
    @Autowired
    EmailService emailService;

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
	public String deleteCustomer(@RequestBody Customer cust) {
    	String response= service.deleteCustomer(cust);
		emailService.sendAccountStatusEmail(cust.getEmail(), cust.getName(), false);
		return response;
	}
    
    @PostMapping("/acceptCustomer")
    public String acceptCustomer(@RequestBody Customer cust) {
    	String response= service.acceptCustomer(cust);
    	emailService.sendAccountStatusEmail(cust.getEmail(), cust.getName(), true);
    	return response;
    }
    
    @PostMapping("/rejectCustomer")
    public String rejectCustomer(@RequestBody Customer cust) {
    	String response= service.rejectCustomer(cust);
    	emailService.sendAccountStatusEmail(cust.getEmail(), cust.getName(), false);
    	return response;
    }
    
    
}
