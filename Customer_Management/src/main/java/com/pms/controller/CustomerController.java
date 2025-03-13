package com.pms.controller;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.pms.entity.Customer;
import com.pms.exception.InvalidEntityException;
import com.pms.service.CustomerService;
import com.pms.service.EmailService;

import jakarta.persistence.PrePersist;
import jakarta.validation.Valid;

@RestController
@RequestMapping("/customer")
public class CustomerController {
	@Autowired
	CustomerService service;
	
//	@PostMapping("/register")
//	public String addCustomer(@RequestBody Customer cust) {
//		return service.addCustomer(cust);
//		
//	}
	
	@PostMapping("/register")
    public ResponseEntity<String> addCustomer(@Valid @RequestBody Customer cust) {   
        String response = service.addCustomer(cust);
        return ResponseEntity.ok(response);
    }
	
	@PostMapping("/login")
	public String login(@RequestBody Customer cust) throws InvalidEntityException {
		return service.login(cust.getEmail(), cust.getPassword());
	}

	@GetMapping("/viewCustomer/{id}")
	public ResponseEntity<?> viewCustomerById(@PathVariable String id) {
		Optional<Customer> customer = service.viewCustomerById(id);
		return customer.isPresent() ? ResponseEntity.ok(customer.get()) : ResponseEntity.badRequest().body("Customer not found");
	}

	
	@PostMapping("/updateCustomer")
	public String updateCustomer(@RequestBody Customer cust) throws InvalidEntityException {
		return service.updateCustomer(cust.getId(),cust);
	}
	
	@PostMapping("/deleteCustomer")
	public String deleteCustomer(@RequestBody Customer cust) throws InvalidEntityException {
		return service.deleteCustomer(cust);
	}
	
}

