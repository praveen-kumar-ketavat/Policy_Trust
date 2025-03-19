package com.pms.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.pms.entity.Customer;

@Repository
public interface CustomerRepository extends JpaRepository<Customer,String> {
	Optional<Customer> findByName(String name);

	Optional<Customer> findByEmail(String email);

	List<Customer> findByVerifiedTrueAndActiveTrue();

	List<Customer> findByVerifiedFalseAndActiveTrue();

	List<Customer> findByVerifiedFalseAndActiveFalse();

	List<Customer> findByNameContainingIgnoreCase(String name);

	List<Customer> findByAgeBetween(int minAge, int maxAge);
}
