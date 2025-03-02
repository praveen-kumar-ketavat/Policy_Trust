package com.pms.controller;

import java.util.List;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.pms.entity.Customer;
import com.pms.service.CustomerService;
import com.pms.service.EmailService;

import jakarta.persistence.PrePersist;

@RestController
@RequestMapping("/customer")
public class CustomerController {
	@Autowired
	CustomerService service;
	@Autowired
    EmailService emailService;  
	
	@PostMapping("/register")
	public String addCustomer(@RequestBody Customer cust) {
		String response = service.addCustomer(cust);
		String subject = "Welcome to Policy Trust!";
        String body = "Dear " + cust.getName() + ",\n\nYour registration is successful. Thank you for joining us!.\n\n You will be notified once you get verified by Admin";
        emailService.sendEmail(cust.getEmail(), subject, body);
        
        return response;
	}
	
	@PostMapping("/login")
	public String login(@RequestBody Customer cust) {
		return service.login(cust.getEmail(), cust.getPassword());
	}
	
//	@GetMapping("/viewCustomers")
//	public List<Customer> viewCustomers(){
//		return service.viewCustomers();
//	}
	
	@PostMapping("/updateCustomer")
	public String updateCustomer(@RequestBody Customer cust) {
		return service.updateCustomer(cust.getId(),cust);
	}
	
	@PostMapping("/deleteCustomer")
	public String deleteCustomer(@RequestBody Customer cust) {
		return service.deleteCustomer(cust);
	}
	
}

