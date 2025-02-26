package com.pms.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.client.RestTemplate;

import com.pms.entity.Admin;
import com.pms.entity.Customer;

@Controller
public class AdminUIController {
	private final String BASE_URL = "http://localhost:8030"; // Backend service

	@Autowired
	private RestTemplate restTemplate;

	@GetMapping("/adminLogin")
	public String showLoginForm(Model model) {
	    model.addAttribute("admin", new Admin());
	    return "adminLogIn";
	}

	@PostMapping("/loginAdmin")
	public String loginAdmin(@ModelAttribute Admin admin, Model model) {
	    ResponseEntity<String> response = restTemplate.postForEntity(
	            BASE_URL + "/admin/login", admin, String.class);

	    String result = response.getBody();

	    if ("Login successful".equals(result)) {
	        return "adminDashboard";
	    } else {
	    	model.addAttribute("error", result);
	        return "adminLogIn";
	    }
	}
	
	@GetMapping("/adminDashboard")
	public String dashboardPage() {
	    return "adminDashboard";
	}
	
	@GetMapping("/fetchCustomers")
	public String fetchCustomers(Model model) {
	    ResponseEntity<List> response = restTemplate.getForEntity(BASE_URL + "/admin/viewAllCustomers", List.class);
	    model.addAttribute("customers", response.getBody());
	    return "customerList";
	}

	@GetMapping("/unverifiedCustomers")
	public String unverifiedCustomers(Model model) {
	    ResponseEntity<List> response = restTemplate.getForEntity(BASE_URL + "/admin/viewUnverifiedCustomers", List.class);
	    model.addAttribute("customers", response.getBody());
	    return "unVerifiedList";
	}

	
}
