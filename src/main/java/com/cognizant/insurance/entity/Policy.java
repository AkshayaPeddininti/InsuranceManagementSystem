package com.cognizant.insurance.entity;


import java.util.Set;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.Id;
import lombok.Data;

@Entity
@Data
public class Policy {
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private int policyID;
	private String policyName;
	private float premiumAmount;
	private String coverageDetails;
	private float validityPeriod;
	
	@ManyToMany
	private Set<Customer> customer;
	
	@ManyToOne
	@JoinColumn(name="agentID")
	private Agent agent;
	
	
	
}
