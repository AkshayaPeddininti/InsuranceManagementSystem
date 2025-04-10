package com.cognizant.insurance.dto;

import com.cognizant.insurance.entity.Role;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Data
@Entity
public class AgentDTO {
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private int agentID;
	
	@Column
    @NotBlank(message = "Agent name cannot be blank")
    @Size(min = 2, max = 50, message = "Agent name must be between 2 and 50 characters")
    @Pattern(regexp = "^[a-zA-Z\\s]+$", message = "Agent name must contain only letters and spaces")
	private String agentName;
	
	
	@Column
    @NotBlank(message = "Agent address cannot be blank")
    @Size(min = 5, max = 100, message = "Agent address must be between 5 and 100 characters")
	private String contactInfo;
	
	@ManyToOne
	@JoinColumn(name="roleID")
	@NotNull(message = "Role cannot be null")
	private Role role;
	
}
