package com.pms.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.pms.entity.Scheme;
import com.pms.repository.SchemeRepository;

@Service
public class SchemeService {
	
	@Autowired
	SchemeRepository repo;

	public List<Scheme> viewSchemes() {
		return repo.findAll();
	}

}
