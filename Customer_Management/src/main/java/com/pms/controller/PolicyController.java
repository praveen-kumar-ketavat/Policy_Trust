package com.pms.controller;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.pms.entity.Customer;
import com.pms.entity.Policy;
import com.pms.entity.Scheme;
import com.pms.service.CustomerService;
import com.pms.service.PolicyService;

@RestController
@RequestMapping("/policy")
public class PolicyController {
	
	@Autowired
	PolicyService service;
	
	@GetMapping("/viewPolicies")
	public List<Policy> viewPolicies(){
		return service.viewPolicies();
	}

	@PostMapping("/viewPolicyDetails")
	public Optional<Policy> viewPolicyDetails(@RequestBody Policy p){
		return service.viewPolicyDetails(p);
	}
	
	@PostMapping("/viewCustPolicies")
	public List<Policy> viewCustPolicies(@RequestBody Customer cust){
		return service.viewCustPolicies(cust.getId());
	}
	
	@PostMapping("/viewSchemePolicies")
	public List<Policy> viewSchemePolicies(@RequestBody Scheme sc){
		return service.viewSchemePolicies(sc.getId());
	}
	
	


}
