package com.pms.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.pms.entity.Customer;

@Repository
public interface CustomerRepository extends JpaRepository<Customer,String> {
	Optional<Customer> findByName(String name);

	List<Customer> findByVerifiedFalse();

	List<Customer> findByVerifiedTrue();

	Optional<Customer> findByEmail(String email);
}
