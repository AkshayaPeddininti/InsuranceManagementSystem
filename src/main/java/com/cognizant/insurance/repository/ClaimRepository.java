package com.cognizant.insurance.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.cognizant.insurance.entity.Claim;

public interface ClaimRepository extends JpaRepository<Claim, Integer>{

}
