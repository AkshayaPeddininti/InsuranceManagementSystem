package com.cognizant.insurance.dto;

import java.util.Set;

import com.cognizant.insurance.entity.Agent;
import com.cognizant.insurance.entity.Customer;

import jakarta.persistence.Column;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.Size;
import lombok.Data;
@Data
public class PolicyDTO {
	@Id
	@NotNull
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private int policyID;
	
	
    @NotBlank(message = "Policy name cannot be blank")
    @Size(min = 2, max = 50, message = "Policy name must be between 2 and 50 characters")
    @Pattern(regexp = "^[a-zA-Z\\s]+$", message = "Policy name must contain only letters and spaces")
	private String policyName;
	

    @NotNull
    @Positive
	private float premiumAmount;
    

    @NotNull
    @Size(max = 500)
	private String coverageDetails;
    

    @NotNull
    @Positive
	private float validityPeriod;
	
	
	
	private Set<Customer> customer;
	private Agent agent;

}
