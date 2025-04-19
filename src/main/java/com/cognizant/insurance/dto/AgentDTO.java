package com.cognizant.insurance.dto;





import lombok.Data;

@Data
public class AgentDTO {
	
	private int userId;
	private String name;
	private String password;
	private String email;
	private String role;
	
	private Long phno;

}
