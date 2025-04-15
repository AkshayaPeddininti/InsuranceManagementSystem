package com.cognizant.insurance.entity;
import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnore;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;

import jakarta.persistence.Entity;

import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.MapsId;
import jakarta.persistence.OneToOne;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Entity
@Data
public class Agent {
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private int agentID;
	
	@MapsId
	@OneToOne(cascade = CascadeType.ALL)
	@JoinColumn(name="agent_id")
	@JsonBackReference
	@JsonIgnore
	private Users user;
	
	@Column
    @NotBlank(message = "Agent name cannot be blank")
    @Size(min = 2, max = 50, message = "Agent name must be between 2 and 50 characters")
    @Pattern(regexp = "^[a-zA-Z\\s]+$", message = "Agent name must contain only letters and spaces")
	private String agentName;
	
	
	@Column
    @NotBlank(message = "Agent address cannot be blank")
    @Size(min = 5, max = 100, message = "Agent address must be between 5 and 100 characters")
	private String contactInfo;
	

	
 
	
}
