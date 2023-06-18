package com.project.electricityBillManagement.repo;

import com.project.electricityBillManagement.model.Admin;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

@Repository
@Transactional
public interface AdminRepository extends JpaRepository<Admin,Integer> {
    Admin findAdminByEmail(String email);
    Admin findAdminByAdminId(int adminId);
}
