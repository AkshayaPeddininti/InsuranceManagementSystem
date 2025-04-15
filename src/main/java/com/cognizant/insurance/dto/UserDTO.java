package com.cognizant.insurance.dto;

import jakarta.persistence.Id;
import jakarta.validation.constraints.NotNull;
import lombok.Data;



@Data
public class UserDTO {
    @Id
    private int id;
    @NotNull
    private String username;
    @NotNull
    private String password;
    @NotNull
    private String role;
    
}