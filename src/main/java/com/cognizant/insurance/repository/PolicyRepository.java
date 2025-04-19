package com.cognizant.insurance.repository;

import java.util.List;


import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.cognizant.insurance.entity.Agent;
import com.cognizant.insurance.entity.Policy;
@Repository
public interface PolicyRepository extends JpaRepository<Policy, Integer> {
//
// List<Policy> findByAgentId(Agent agent);



}
