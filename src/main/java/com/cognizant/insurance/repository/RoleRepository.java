package com.cognizant.insurance.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.cognizant.insurance.entity.Role;

public interface RoleRepository extends JpaRepository<Role, Integer> {

}
