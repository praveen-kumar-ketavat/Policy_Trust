package com.pms.repository;


import com.pms.entity.Policy;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

public interface PolicyRepository extends JpaRepository<Policy, Long> {


	List<Policy> findByCustomerId(String id);

	List<Policy> findBySchemeId(int id);
	
	Optional<Policy> findByPolicyId(Long policyId);
}
