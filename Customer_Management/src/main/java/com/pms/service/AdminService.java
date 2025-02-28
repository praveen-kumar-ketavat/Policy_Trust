package com.pms.service;

import com.pms.entity.Admin;
import com.pms.entity.Customer;
import com.pms.repository.AdminRepository;
import com.pms.repository.CustomerRepository;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class AdminService {

    @Autowired
    AdminRepository adminRepo;
    @Autowired
    CustomerRepository custRepo;

    public String login(String email, String password) {
        Admin admin = adminRepo.findByEmailAndPassword(email, password);
        return admin != null ? "Login successful" : "Invalid credentials";
    }

    public List<Customer> getVerifiedCustomers() {
        return custRepo.findByVerifiedTrueAndActiveTrue();
    }

	public List<Customer> getUnverifiedCustomers() {
		// TODO Auto-generated method stub
		return custRepo.findByVerifiedFalseAndActiveTrue();
	}
	
	public List<Customer> getDeletedCustomers() {
		// TODO Auto-generated method stub
		return custRepo.findByVerifiedFalseAndActiveFalse();
	}
	

	public String deleteCustomer(Customer cust) {
		 Optional<Customer> existingCustomer = custRepo.findById(cust.getId());
		 if (existingCustomer.isPresent()) {
		        Customer customer = existingCustomer.get();
		        customer.setVerified(false);
		        customer.setActive(false);
		        custRepo.save(customer);
		        return "Customer deleted";
		    } else {
		        return "Customer not found";
		    }
	}

	public String acceptCustomer(Customer cust) {
		 Optional<Customer> existingCustomer = custRepo.findById(cust.getId());
		 if (existingCustomer.isPresent()) {
		        Customer customer = existingCustomer.get();
		        customer.setVerified(true);
		        customer.setActive(true);
		        custRepo.save(customer);
		        return "Customer accepted";
		    } else {
		        return "Customer not found";
		    }
	}

	public String rejectCustomer(Customer cust) {
		 Optional<Customer> existingCustomer = custRepo.findById(cust.getId());
		 if (existingCustomer.isPresent()) {
		        Customer customer = existingCustomer.get();
		        customer.setVerified(false);
		        customer.setActive(false);
		        custRepo.save(customer);
		        return "Customer rejected";
		    } else {
		        return "Customer not found";
		    }
	}
	
}
