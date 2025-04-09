package com.cognizant.insurance.entity;
 
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.Data;
@Data
@Entity
@Table(name="Customer")
public class Customer {
    @Id
    @GeneratedValue(strategy=GenerationType.IDENTITY)
    private int customerID;
    @Column
    private String customerName;
    @Column
    private String customerEmail;
    @Column
    private String customerPhoneNum;
    @Column
    private String customerAddress;
    @ManyToOne
    @JoinColumn(name="roleID")
    private Role role;
    
	
}