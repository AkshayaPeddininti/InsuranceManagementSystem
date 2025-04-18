package com.cognizant.insurance.dto;







import com.cognizant.insurance.entity.Role;

import lombok.Data;

@Data
public class CustomerDTO {
	private int userId;
	
	private String name;
	
	private String password;
	
	private String email;
	
	private Role role;
	
	private int phno;
	
}
