package com.cognizant.insurance.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.cognizant.insurance.entity.Policy;

public interface PolicyRepository extends JpaRepository<Policy, Integer> {

}
