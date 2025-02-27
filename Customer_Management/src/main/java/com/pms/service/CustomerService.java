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
import com.pms.repository.CustomerRepository;

import jakarta.persistence.PrePersist;

@Service
public class CustomerService {

	@Autowired
	CustomerRepository repo;
	@PrePersist
    public String generateId() {
        //return UUID.randomUUID().toString();
		long count = repo.count() + 1;
		return "C" + String.format("%03d", count);//C001
    }
//	public String addCustomer(Customer cust) {
//		// TODO Auto-generated method stub
//		cust.setId(generateId());
//		cust.setVerified(false);
//		repo.save(cust);
//		return "Customer added successfully";
//	}
	
	public String addCustomer(Customer cust) {
		Optional<Customer> existingCustomer = repo.findByEmail(cust.getEmail());
	    
	    if (existingCustomer.isPresent()) {
	        return "Customer already exists";
	    }
        cust.setId(generateId());
        cust.setVerified(false);
        cust.setActive(false);
        cust.setregDate(LocalDate.now().toString());
        repo.save(cust);
        return cust.getId();
    }
	

	public String login(String email, String password) {
		// TODO Auto-generated method stub
		Customer customer = repo.findByEmail(email).orElse(null);

	    if (customer == null) {
	        return "User not found";
	    }
	    if (!customer.getVerified()) {
	        return "Your account is not verified.";
	    }
	    if (!customer.getPassword().equals(password)) {
	        return "Invalid credentials";
	    }
	    return "Login successful";
	}


//	public List<Customer> viewCustomers() {
//		// TODO Auto-generated method stub
//		return repo.findAll();
//	}

//	public String updateCustomer(String id, String name, Customer cust) {
//	    Optional<Customer> existingCustomer = repo.findById(id);
//
//	    if (existingCustomer.isPresent() && existingCustomer.get().getName().equals(name)) {
//	        Customer customer = existingCustomer.get();
//	        
//	        if (cust.getName() != null) customer.setName(cust.getName());
//	        if (cust.getPhone() != null) customer.setPhone(cust.getPhone());
//	        if (cust.getEmail() != null) customer.setEmail(cust.getEmail());
//	        if (cust.getAddress() != null) customer.setAddress(cust.getAddress());
//	        if (cust.getDateofreg() != null) customer.setDateofreg(cust.getDateofreg());
//
//	        repo.save(customer);
//	        return "Customer details updated successfully";
//	    } 
//	    return "Customer not found";
//	}


}
