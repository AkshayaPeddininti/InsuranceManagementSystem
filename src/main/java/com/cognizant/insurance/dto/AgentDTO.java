package com.cognizant.insurance.dto;

import lombok.Data;

@Data
public class AgentDTO {
    private int agentID;
    private String agentName;
    private String contactInfo;
    private int roleID; // Represents the role ID from the Role entity
	
}
