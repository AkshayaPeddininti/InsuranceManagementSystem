package com.cognizant.insurance.service;

import java.util.Optional;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import com.cognizant.insurance.dto.CustomerDTO;
import com.cognizant.insurance.entity.Customer;
import com.cognizant.insurance.exception.AllException.CustomerDetailNotFound;
import com.cognizant.insurance.repository.CustomerRepository;



@Service
public class CustomerService {
@Autowired
	ModelMapper modelMapper;
@Autowired
CustomerRepository customerRepository ;

BCryptPasswordEncoder encoder=new BCryptPasswordEncoder(12);

	public CustomerDTO addCustomer(CustomerDTO customerDTO){
		
		Customer customer= modelMapper.map(customerDTO,Customer.class);
		customer.setPassword(encoder.encode(customer.getPassword()));
		customer=customerRepository.save(customer);
		
		return modelMapper.map(customer, CustomerDTO.class);
	}
	
	
	public String removeCustomer(int customerId) {
	Optional<Customer> container=customerRepository.findById(customerId);
	if(!container.isPresent()) {
		return "failed no one";
	}
	Customer customer=container.get();
	customerRepository.delete(customer);
	return "successful";
	
	}
	
	
	public CustomerDTO viewCustomer(int customerId) {
Customer customer=customerRepository.findById(customerId).orElseThrow(
	()->new CustomerDetailNotFound("Customer with Id "+customerId+" not found."));
		return modelMapper.map(customer,CustomerDTO.class);
	}
	 public CustomerDTO updateCustomerDetails(int customerId, String phno) {
	        Customer customer = customerRepository.findById(customerId)
	                .orElseThrow(() -> new CustomerDetailNotFound("Student with Id " + customerId + " not found."));
	        customer.setPhno(phno);
	       
	        Customer updatedCustomer = customerRepository.save(customer);
	        return modelMapper.map(updatedCustomer, CustomerDTO.class);
	    }

	
}
