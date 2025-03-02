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
	    if (cust.getPhone().length() > 10) {
	        return "Phone number cannot exceed 10 digits";
	    }
        cust.setId(generateId());
        cust.setVerified(false);
        cust.setActive(true);
        cust.setregDate(LocalDate.now().toString());
        repo.save(cust);
        
        return cust.getId();
    }
	

	public String login(String email, String password) {
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


//	public List<Customer> viewCustomers() {
//		// TODO Auto-generated method stub
//		return repo.findAll();
//	}

	public String updateCustomer(String id, Customer cust) {
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

	public String deleteCustomer(Customer cust) {
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


}
