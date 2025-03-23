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
	

//	public String login(String email, String password) throws InvalidEntityException {
//		Customer customer = repo.findByEmail(email).orElse(null);
//
//	    if (customer == null) {
//	        return "User not found";
//	    }
//	    if (!customer.getActive()) {
//	        return "Your account is deleted/rejected. Kindly register with new email.";
//	    }
//	    if (!customer.getVerified()) {
//	        return "Your account is not verified.";
//	    }
//	    if (!customer.getPassword().equals(password)) {
//	        return "Invalid credentials";
//	    }
//	    return "Login successful";
//	}
	
	public Customer login(String email, String password) throws InvalidEntityException {
	    Customer customer = repo.findByEmail(email).orElse(null);

	    if (customer == null) {
	        throw new InvalidEntityException("User doesn't exist. Register your account.");
	    }
	    if (!customer.getActive()) {
	        throw new InvalidEntityException("Your account is deleted/rejected. Kindly register with a new email.");
	    }
	    if (!customer.getVerified()) {
	        throw new InvalidEntityException("Your account is not verified.");
	    }
	    if (!customer.getPassword().equals(password)) {
	        throw new InvalidEntityException("Invalid credentials");
	    }
	    
	    return customer;
	}



	public Customer updateCustomer(String id, Customer cust) throws InvalidEntityException {
	    Optional<Customer> existingCustomer = repo.findById(id);

	    Customer customer = existingCustomer.get();
        
        if (cust.getName() != "") customer.setName(cust.getName());
        if (cust.getAge()!=null) customer.setAge(cust.getAge());
        if (cust.getPhone() != "") customer.setPhone(cust.getPhone());
        if (cust.getAddress() != "") customer.setAddress(cust.getAddress());
        repo.save(customer);
        
        String subject = "Your Account Has Been Updated";
	    String body = "Dear " + customer.getName() + ",\n\n"
	                + "Your account has been successfully updated.\n\n"
	                + "Best regards,\n"
	                + "Policy Trust";
	    
	    emailService.sendEmail(customer.getEmail(), subject, body);
        
        return customer;
	}

	public String deleteCustomer(Customer cust) throws InvalidEntityException {
		 Optional<Customer> existingCustomer = repo.findById(cust.getId());
		 if (existingCustomer.isPresent()) {
		        Customer customer = existingCustomer.get();
		        customer.setVerified(false);
		        customer.setActive(false);
		        repo.save(customer);
		        
		        String subject = "Your Account is Deactivated";
			    String body = "Dear " + customer.getName() + ",\n\n"
			                + "Your account has been successfully Deactivated.\n\n"
			                + "Best regards,\n"
			                + "Policy Trust";
			    
			    emailService.sendEmail(customer.getEmail(), subject, body);
			    
		        return "Customer deleted";
		    } else {
		        return "Customer not found";
		    }
	}

	public Customer changeCustomerPassword(String id, Customer cust) {
		// TODO Auto-generated method stub
		 Optional<Customer> existingCustomer = repo.findById(id);
		 Customer customer = existingCustomer.get();
		 if (cust.getPassword() != "") customer.setPassword(cust.getPassword());
		 repo.save(customer);
		 
		 String subject = "Your Password Has Been Changed";
		    String body = "Dear " + customer.getName() + ",\n\n"
		                + "Your account password has been successfully updated.\n\n"
		                + "Best regards,\n"
		                + "Policy Trust";
		    
		    emailService.sendEmail(customer.getEmail(), subject, body);
		    
	     return customer;
	}


}
