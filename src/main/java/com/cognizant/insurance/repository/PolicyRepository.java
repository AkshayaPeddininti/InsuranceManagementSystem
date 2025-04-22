package com.cognizant.insurance.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.cognizant.insurance.entity.Policy;
@Repository
public interface PolicyRepository extends JpaRepository<Policy, Integer> {

	Optional<Policy> findByAgentUserId(int userId);

//	List<Policy> findByAgentId(Agent agent);

	



}
