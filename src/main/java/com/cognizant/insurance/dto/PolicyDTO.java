package com.cognizant.insurance.dto;

import java.util.Set;

import com.cognizant.insurance.entity.Agent;
import com.cognizant.insurance.entity.Customer;

import lombok.Data;
@Data
public class PolicyDTO {
	private int policyID;
	private String policyName;
	private float premiumAmount;
	private String coverageDetails;
	private float validityPeriod;
	private Set<Customer> customer;
	private Agent agent;

}
