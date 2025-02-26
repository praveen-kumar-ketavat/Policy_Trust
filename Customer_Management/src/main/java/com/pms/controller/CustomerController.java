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

import jakarta.persistence.PrePersist;

@RestController
@RequestMapping("/customer")
public class CustomerController {
	@Autowired
	CustomerService service;
	
	@PostMapping("/register")
	public String addCustomer(@RequestBody Customer cust) {
		return service.addCustomer(cust);
	}
	
	@PostMapping("/login")
	public String login(@RequestBody Customer cust) {
		return service.login(cust.getName(), cust.getPassword());
	}
	
//	@GetMapping("/register")
//	public String register() {
//		return "register";
//	}
//	@GetMapping("/viewCustomers")
//	public List<Customer> viewCustomers(){
//		return service.viewCustomers();
//	}
	
//	@PostMapping("/updateCustomer")
//	public String updateCustomer(@RequestParam  String id,@RequestParam  String name,@RequestBody Customer cust) {
//		return service.updateCustomer(id,name,cust);
//	}
}

