package com.pms.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.client.RestTemplate;

import com.pms.entity.Customer;

@Controller
public class CustomerUIController {

	private final String BASE_URL = "http://localhost:8030"; // Backend service

	@Autowired
	private RestTemplate restTemplate;

	@GetMapping("/")
	public String home() {
		return "home";
	}

	@GetMapping("/register")
	public String showRegisterForm(Model model) {
		model.addAttribute("customer", new Customer());
		return "register";
	}

	@PostMapping("/addCustomer")
	public String registerCustomer(@ModelAttribute Customer cust, Model model) {
	    ResponseEntity<String> response = restTemplate.postForEntity(
	            BASE_URL + "/customer/register", cust, String.class);
	    String responseBody = response.getBody();

	    if ("Customer already exists".equals(responseBody)) {
	        model.addAttribute("error", "Customer already exists. Please try logging in.");
	        return "register";
	    }
	    if ("Phone number cannot exceed 10 digits".equals(responseBody)) {
	        model.addAttribute("error", "Phone number cannot exceed 10 digits");
	        return "register";
	    }

	    model.addAttribute("customerId", responseBody);

	    return "message";
	}
	
	@GetMapping("/login")
	public String showLoginForm(Model model) {
	    model.addAttribute("customer", new Customer());
	    return "login";
	}
	
	@PostMapping("/loginCust")
	public String loginCustomer(@ModelAttribute Customer cust, Model model) {
	    ResponseEntity<String> response = restTemplate.postForEntity(
	            BASE_URL + "/customer/login", cust, String.class);

	    String result = response.getBody();

	    if ("Login successful".equals(result)) {
	        return "redirect:/dashboard";
	    } else {
	        model.addAttribute("error", result);
	        return "login";
	    }
	}
	
	@GetMapping("/dashboard")
	public String dashboardPage() {
	    return "dashboard";
	}
}
