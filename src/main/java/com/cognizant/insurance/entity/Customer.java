package com.cognizant.insurance.entity;




import jakarta.persistence.Entity;
import lombok.Data;



@Entity
@Data
public class Customer extends Users{
	private Long phno;
}

