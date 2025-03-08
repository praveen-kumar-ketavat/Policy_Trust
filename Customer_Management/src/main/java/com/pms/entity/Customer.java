package com.pms.entity;

import java.util.List;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import jakarta.validation.Valid;
import jakarta.validation.constraints.*;

@Entity
@Table(name = "customer")
public class Customer {
	
	@Valid
	@Id
	@Column(name = "id")
    private String id;

	@Column(name = "name")
    @NotEmpty(message = "Name is required")
    private String name;

    @Column(name = "phone")
    @NotEmpty(message = "Phone number is required")
    @Pattern(regexp = "^[0-9]{10}$", message = "Phone number must be of 10 digits and Numbers only")
    private String phone;

    @Column(name = "email")
    @NotEmpty(message = "Email is required")
    @Email(message = "Invalid email format")
    private String email;
    
    @Column(name = "address")
    @NotEmpty(message = "Address is required")
    private String address;

    @Column(name = "regDate")
    private String regDate;
    
    @Column(name="password")
    @NotEmpty(message = "Password is required")
    @Size(min = 6, message = "Password must be at least 6 characters long")
    @Pattern(
        regexp = "^(?=.*[A-Z])(?=.*[@$!%*?&])(?=.*[0-9])[A-Za-z\\d@$!%*?&]{6,}$",
        message = "Password must contain at least one uppercase letter, one special character, and one number"
    )
    private String password;

    
    @Column(name="verified")
    private boolean verified;
    
    @Column(name="active")
    private boolean active;
    
    @OneToMany(mappedBy = "customer", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Policy> policies;

    @OneToMany(mappedBy = "customer", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Feedback> feedbacks;
    
	public String getId() {
        return id;
    }
    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }
    public void setName(String name) {
        this.name = name;
    }
    
    public String getPhone() {
        return phone;
    }
    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getEmail() {
        return email;
    }
    public void setEmail(String email) {
        this.email = email;
    }
    
    public String getAddress() {
        return address;
    }
    public void setAddress(String address) {
        this.address = address;
    }

    public String getRegDate() {
        return regDate;
    }
    public void setRegDate(String regDate) {
        this.regDate = regDate;
    }
    
    public boolean getVerified() {
        return verified;
    }
    public void setVerified(boolean verified) {
        this.verified = verified;
    }
    
    public String getPassword() {
        return password;
    }
    public void setPassword(String password) {
        this.password = password;
    }
    
    public boolean getActive() {
        return active;
    }
    public void setActive(boolean active) {
        this.active = active;
    }
	
}
