package com.pms.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.pms.entity.Policy;
import com.pms.entity.Scheme;
import com.pms.service.PolicyService;
import com.pms.service.SchemeService;

@RestController
@RequestMapping("/scheme")
public class SchemeController {

	@Autowired
	SchemeService service;
	
	@GetMapping("/viewSchemes")
	public List<Scheme> viewSchemes(){
		return service.viewSchemes();
	}
}
