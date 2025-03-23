package com.pms.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.pms.entity.Policy;
import com.pms.entity.Scheme;

public interface SchemeRepository extends JpaRepository<Scheme, Integer> {

}
