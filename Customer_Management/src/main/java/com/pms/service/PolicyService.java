package com.pms.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.pms.entity.Admin;
import com.pms.entity.Policy;
import com.pms.exception.InvalidEntityException;
import com.pms.repository.CustomerRepository;
import com.pms.repository.PolicyRepository;

@Service
public class PolicyService {

	@Autowired
	PolicyRepository repo;

	public List<Policy> viewPolicies() {
		return repo.findAll();
	}

	public Optional<Policy> viewPolicyDetails(Policy p) {
		  Optional<Policy> pol = repo.findById(p.getPolicyId());
	        return pol;
	}

	public List<Policy> viewCustPolicies(String id) {
		return repo.findByCustomerId(id);
	}

	public List<Policy> viewSchemePolicies(int id) {
		return repo.findBySchemeId(id);
	}
}
