package com.cognizant.insurance.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.cognizant.insurance.entity.Customer;

public interface CustomerRepository extends JpaRepository<Customer, Integer>{

}
