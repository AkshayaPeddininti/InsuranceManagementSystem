package com.cognizant.insurance.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.cognizant.insurance.entity.Claim;
@Repository
public interface ClaimRepository extends JpaRepository<Claim, Integer>{

}
