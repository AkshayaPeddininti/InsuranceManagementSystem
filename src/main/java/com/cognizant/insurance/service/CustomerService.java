package com.cognizant.insurance.service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import com.cognizant.insurance.dto.CustomerDTO;
import com.cognizant.insurance.dto.ReturnUserDTO;
import com.cognizant.insurance.dto.UserDTO;
import com.cognizant.insurance.entity.Customer;
import com.cognizant.insurance.exception.AllException.CustomerDetailNotFound;
import com.cognizant.insurance.repository.CustomerRepository;

@Service
public class CustomerService {
	@Autowired
	ModelMapper modelMapper;
	@Autowired
	CustomerRepository customerRepository;

	BCryptPasswordEncoder encoder = new BCryptPasswordEncoder(12);

	public CustomerDTO addCustomer(CustomerDTO customerDTO) {

		Customer customer = modelMapper.map(customerDTO, Customer.class);
		customer.setPassword(encoder.encode(customer.getPassword()));
		
		customer = customerRepository.save(customer);

		return modelMapper.map(customer, CustomerDTO.class);
	}

	public String removeCustomer(int customerId) {
		Optional<Customer> container = customerRepository.findById(customerId);
		if (!container.isPresent()) {
			return "failed no one";
		}
		Customer customer = container.get();
		customerRepository.delete(customer);
		return "successful";

	}

	public CustomerDTO viewCustomer(int customerId) {
		Customer customer = customerRepository.findById(customerId)
				.orElseThrow(() -> new CustomerDetailNotFound("Customer with Id " + customerId + " not found."));
		return modelMapper.map(customer, CustomerDTO.class);
	}

	public String updateCustomer(int id,CustomerDTO customerDTO) {
		// TODO Auto-generated method stub
		Customer customer = customerRepository.findById(id)
				.orElseThrow(() -> new CustomerDetailNotFound("Customer with Id " + id + " not found."));
	
		if(customerDTO.getName()!=null)customer.setName(customerDTO.getName());
		if(customerDTO.getEmail()!=null)customer.setEmail(customerDTO.getEmail());
		if(customerDTO.getPhno()!=null)customer.setPhno(customerDTO.getPhno());
		if(customerDTO.getPassword()!=null)customer.setPassword(encoder.encode(customerDTO.getPassword()));
		customerRepository.save(customer);
		return "Created";
	}

	public List<ReturnUserDTO> getAllCustomer() {
		// TODO Auto-generated method stub
		List<Customer> customers=customerRepository.findAll();
		return customers.stream()
                .map(customer -> modelMapper.map(customer, ReturnUserDTO.class))
                .collect(Collectors.toList());
	}
	
	
	

}
