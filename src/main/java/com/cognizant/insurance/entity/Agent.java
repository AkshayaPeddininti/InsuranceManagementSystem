package com.cognizant.insurance.entity;
import jakarta.persistence.Entity;

import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import lombok.Data;

@Entity
@Data
public class Agent {
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private int agentID;
	private String agentName;
	private String contactInfo;
	
	@ManyToOne
	@JoinColumn(name="roleID")
	private Role role;
	
//	@OneToMany
//	@JoinColumn(name="customerID")
//	private List<Policy> policies; 
	
}
