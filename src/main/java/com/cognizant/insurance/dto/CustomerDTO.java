package com.cognizant.insurance.dto;
import com.cognizant.insurance.entity.Role;

import jakarta.persistence.Id;
import lombok.Data;
@Data
public class CustomerDTO{
    @Id
    private int customerID;
    private String customerName;
    private String customerEmail;
    private String customerPhoneNum;
    private String customerAddress;
    private Role role;
    
	
}