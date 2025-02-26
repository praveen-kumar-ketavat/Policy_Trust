package com.pms.service;

import com.pms.entity.Admin;
import com.pms.entity.Customer;
import com.pms.repository.AdminRepository;
import com.pms.repository.CustomerRepository;

import java.util.List;

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
        return custRepo.findByVerifiedTrue();
    }

	public List<Customer> getUnverifiedCustomers() {
		// TODO Auto-generated method stub
		return custRepo.findByVerifiedFalse();
	}
}
