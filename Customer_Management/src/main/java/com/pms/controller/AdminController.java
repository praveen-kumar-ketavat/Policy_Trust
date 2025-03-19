package com.pms.controller;

import com.pms.entity.Admin;
import com.pms.entity.Customer;
import com.pms.exception.InvalidEntityException;
import com.pms.service.AdminService;
import com.pms.service.CustomerService;
import com.pms.service.EmailService;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/admin")
public class AdminController {

    @Autowired
    AdminService service;

    @Autowired
    CustomerService custService;

    @PostMapping("/login")
    public ResponseEntity<Admin> login(@RequestBody Admin admin) throws InvalidEntityException {
        Admin loggedInAdmin = service.login(admin.getEmail(), admin.getPassword());
        return ResponseEntity.ok(loggedInAdmin);
    }

    @GetMapping("/viewVerifiedCustomers")
    public List<Customer> viewVerifiedCustomers(){
        return service.getVerifiedCustomers();
    }

    @GetMapping("/viewUnverifiedCustomers")
    public List<Customer> viewUnverifiedCustomers(){
        return service.getUnverifiedCustomers();
    }

    @GetMapping("/viewDeletedCustomers")
    public List<Customer> viewDeletedCustomers(){
        return service.getDeletedCustomers();
    }

    @GetMapping("/viewCustomerById/{id}")
    public ResponseEntity<Customer> viewCustomerById(@PathVariable String id) throws InvalidEntityException {
        Customer customer = service.getCustomerById(id);
        return ResponseEntity.ok(customer);
    }

    @GetMapping("/viewCustomerByName/{name}")
    public List<Customer> viewCustomerByName(@PathVariable String name) {
        return service.getCustomersByName(name);
    }

    @GetMapping("/filterCustomersByAge")
    public List<Customer> filterCustomersByAgeRange(@RequestParam int minAge, @RequestParam int maxAge) {
        return service.getCustomersByAgeRange(minAge, maxAge);
    }

    @PostMapping("/deleteCustomer")
    public String deleteCustomer(@RequestBody Customer cust) throws InvalidEntityException {
        return service.deleteCustomer(cust);
    }

    @PostMapping("/acceptCustomer")
    public String acceptCustomer(@RequestBody Customer cust) throws InvalidEntityException {
        return service.acceptCustomer(cust);
    }

    @PostMapping("/rejectCustomer")
    public String rejectCustomer(@RequestBody Customer cust) throws InvalidEntityException {
        return service.rejectCustomer(cust);
    }
}
