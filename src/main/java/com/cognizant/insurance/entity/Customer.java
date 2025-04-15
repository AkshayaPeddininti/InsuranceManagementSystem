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
import jakarta.persistence.Table;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Data
@Entity
@Table(name="Customer")
public class Customer {

    @Id
    @GeneratedValue(strategy=GenerationType.IDENTITY)
    private int customerID;

    @MapsId
	@OneToOne(cascade = CascadeType.ALL)
	@JoinColumn(name="customer_id")
	@JsonBackReference
	@JsonIgnore
	private Users user;
    
    @Column
    @NotBlank(message = "Customer name cannot be blank")
    @Size(min = 2, max = 50, message = "Customer name must be between 2 and 50 characters")
    @Pattern(regexp = "^[a-zA-Z\\s]+$", message = "Customer name must contain only letters and spaces")
    private String customerName;

    @Column
    @Email(message = "Email should be valid")
    @NotBlank(message = "Customer email cannot be blank")
    private String customerEmail;

    @Column
    @NotBlank(message = "Customer phone number cannot be blank")
    @Pattern(regexp = "^\\d{10}$", message = "Customer phone number must be exactly 10 digits")
    private String customerPhoneNum;

    @Column
    @NotBlank(message = "Customer address cannot be blank")
    @Size(min = 5, max = 100, message = "Customer address must be between 5 and 100 characters")
    private String customerAddress;


}
