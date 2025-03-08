package com.pms.controller;

import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;
import jakarta.validation.Valid;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
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

	@Autowired
	private ObjectMapper objectMapper; // Inject ObjectMapper

//	@PostMapping("/addCustomer")
//	public String registerCustomer(@Valid @ModelAttribute Customer cust, BindingResult result, Model model) {
//	    if (result.hasErrors()) {
//	        return "register";
//	    }
//
//	    try {
//	        ResponseEntity<String> response = restTemplate.postForEntity(
//	                BASE_URL + "/customer/register", cust, String.class);
//	        String responseBody = response.getBody();
//	        if ("Customer already exists".equals(responseBody)) {
//	            model.addAttribute("error", "Customer already exists. Please try logging in.");
//	            return "register";
//	        }
//	        if ("Phone number cannot exceed 10 digits".equals(responseBody)) {
//	            model.addAttribute("error", "Phone number cannot exceed 10 digits");
//	            return "register";
//	        }
//	        model.addAttribute("customerId", response.getBody());
//	        return "message";
//	    } catch (HttpClientErrorException.BadRequest e) {
//	        try {
//	            // Parse JSON response if available
//	            Map<String, String> errors = objectMapper.readValue(e.getResponseBodyAsString(), Map.class);
//	            model.addAttribute("validationErrors", errors);
//	        } catch (JsonProcessingException ex) {
//	            model.addAttribute("error", "An error occurred while processing the response.");
//	        }
//	        return "register";
//	    } catch (Exception e) {
//	        model.addAttribute("error", "An unexpected error occurred. Please try again later.");
//	        return "register";
//	    }
//	}

	
	@PostMapping("/addCustomer")
	public String registerCustomer(@ModelAttribute("customer") @Validated Customer cust, BindingResult result, Model model) {
	    try {
	        ResponseEntity<String> response = restTemplate.postForEntity(BASE_URL + "/customer/register", cust, String.class);
	        String responseBody = response.getBody();

	        if ("Customer already exists".equals(responseBody)) {
	            model.addAttribute("error", "Customer already exists. Please try logging in.");
	            return "register";
	        }

	        model.addAttribute("customerId", response.getBody());
	        return "message"; // Success Page

	    } catch (HttpClientErrorException e) {
	    	 try {
	             Map<String, String> errors = objectMapper.readValue(
	                 e.getResponseBodyAsString(), new TypeReference<Map<String, String>>() {});

	             if (errors != null && !errors.isEmpty()) {
	                 model.addAttribute("validationErrors", errors); // Store errors in model
	             }
	         } catch (JsonProcessingException e1) {
	             model.addAttribute("error", "An error occurred while processing the response.");
	         }

	         return "register";
	    }
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
	        return "dashboard";
	    } else {
	        model.addAttribute("error", result);
	        return "login";
	    }
	}
	
//	@GetMapping("/updateCust")
//	public String showUpdateForm(Model model) {
//	    model.addAttribute("customer", new Customer());
//	    return "updatePage";
//	}
//	
//	@PostMapping("/updateCustomer")
//	public String updateCustomer(@ModelAttribute Customer cust, Model model) {
//
//	    ResponseEntity<String> response = restTemplate.postForEntity(
//	            BASE_URL + "/admin/updateCustomer", cust, String.class);
//
//	    return "redirect:/fetchVerifiedCustomers";
//	}
}
