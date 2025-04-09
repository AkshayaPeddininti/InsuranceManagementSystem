package com.cognizant.insurance.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import com.cognizant.insurance.entity.Agent;

public interface AgentRepository extends JpaRepository<Agent, Integer> {
}
