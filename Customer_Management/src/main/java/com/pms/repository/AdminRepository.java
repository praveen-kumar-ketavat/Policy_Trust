package com.pms.repository;

import com.pms.entity.Admin;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AdminRepository extends JpaRepository<Admin, String> {
//    Admin findByEmailAndPassword(String email, String password);

	Admin findByEmail(String email);
}
