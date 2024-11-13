package com.project01.ecommerce.repository;

import com.project01.ecommerce.entity.UserRole;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface RoleRepository extends JpaRepository<UserRole, Long> {

    List<UserRole> findByRole(String role);
}
