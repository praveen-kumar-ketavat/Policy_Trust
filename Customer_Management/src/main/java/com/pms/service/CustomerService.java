package com.pms.service;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import com.pms.entity.Customer;
import com.pms.exception.InvalidEntityException;
import com.pms.repository.CustomerRepository;

import jakarta.persistence.PrePersist;

@Service
public class CustomerService {

	@Autowired
	CustomerRepository repo;
	@Autowired
    EmailService emailService;  
	@PrePersist
    public String generateId() {
		long count = repo.count() + 1;
		return "C" + String.format("%03d", count);//C001
    }
	
	public String addCustomer(Customer cust) {
		Optional<Customer> existingCustomer = repo.findByEmail(cust.getEmail());
	    
	    if (existingCustomer.isPresent()) {
	        return "Customer already exists";
	    }
        cust.setId(generateId());
        cust.setVerified(false);
        cust.setActive(true);
        cust.setRegDate(LocalDate.now().toString());
        repo.save(cust);
        
        String subject = "Welcome to Policy Trust!";
        String body = "Dear " + cust.getName() + ",\n\nYour registration is successful. Thank you for joining us!.\n\n You will be notified once you get verified by Admin";
        emailService.sendEmail(cust.getEmail(), subject, body);
        
        return cust.getId();
    }
	

	public String login(String email, String password) throws InvalidEntityException {
		Customer customer = repo.findByEmail(email).orElse(null);

	    if (customer == null) {
	        return "User not found";
	    }
	    if (!customer.getActive()) {
	        return "Your account is deleted/rejected. Kindly register with new email.";
	    }
	    if (!customer.getVerified()) {
	        return "Your account is not verified.";
	    }
	    if (!customer.getPassword().equals(password)) {
	        return "Invalid credentials";
	    }
	    return "Login successful";
	}


	public String updateCustomer(String id, Customer cust) throws InvalidEntityException {
	    Optional<Customer> existingCustomer = repo.findById(id);

	    if (existingCustomer.isPresent()) {
	        Customer customer = existingCustomer.get();
	        
	        if (cust.getName() != null) customer.setName(cust.getName());
	        if (cust.getPhone() != null) customer.setPhone(cust.getPhone());
	        if (cust.getEmail() != null) customer.setEmail(cust.getEmail());
	        if (cust.getAddress() != null) customer.setAddress(cust.getAddress());
	        if (cust.getPassword() != null) customer.setPassword(cust.getPassword());

	        repo.save(customer);
	        return "Details updated successfully";
	    } 
	    return "Customer not found";
	}

	public String deleteCustomer(Customer cust) throws InvalidEntityException {
		 Optional<Customer> existingCustomer = repo.findById(cust.getId());
		 if (existingCustomer.isPresent()) {
		        Customer customer = existingCustomer.get();
		        customer.setVerified(false);
		        customer.setActive(false);
		        repo.save(customer);
		        return "Customer deleted";
		    } else {
		        return "Customer not found";
		    }
	}

	public Optional<Customer> viewCustomerById(String id) {
		return repo.findById(id);
	}


}
