package com.cognizant.insurance.service;

import org.modelmapper.ModelMapper;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
import java.util.stream.Collectors;

import com.cognizant.insurance.entity.Agent;
import com.cognizant.insurance.entity.Customer;

import com.cognizant.insurance.entity.Users;
import com.cognizant.insurance.dto.AgentDTO;
import com.cognizant.insurance.dto.CustomerDTO;
import com.cognizant.insurance.repository.CustomerRepository;

import com.cognizant.insurance.repository.UserRepo;

import jakarta.validation.Valid;

@Service
public class CustomerService {

    @Autowired
    private CustomerRepository customerRepository;
    
    @Autowired
    private UserRepo userRepo;
    @Autowired
    PasswordEncoder passwordEncoder;

    @Autowired
    private ModelMapper modelMapper; // Inject ModelMapper bean

    // Convert from Customer to CustomerDTO
    private CustomerDTO convertToDTO(Customer customer) {
        return modelMapper.map(customer, CustomerDTO.class); // Use ModelMapper
    }

    // Convert from CustomerDTO to Customer
    private Customer convertToEntity(CustomerDTO customerDTO) {
        return modelMapper.map(customerDTO, Customer.class); // Use ModelMapper
    }

    // Get all customers
    public List<CustomerDTO> getAllCustomers() {
        return customerRepository.findAll()
                                 .stream()
                                 .map(this::convertToDTO)
                                 .collect(Collectors.toList());
    }

    // Get customer by ID
    public CustomerDTO getCustomerById(int customerID) {
        Customer customer = customerRepository.findById(customerID)
                                              .orElseThrow(() -> new RuntimeException("Customer not found"));
        return convertToDTO(customer);
    }
    
    // Create customer
//    public CustomerDTO createCustomer(@Valid CustomerDTO customerDTO) {
//        // Validate RoleID

//        System.out.println("role id.........." + roleID);
//        
//        if (roleID != 402) {
//            throw new ResponseStatusException(
//                HttpStatus.BAD_REQUEST,
//                "Invalid RoleID. Accepted values are 401 (Agent) and 402 (Customer)."
//            );
//        }
//
//        // Fetch the Role from the database
//        Role role = roleRepository.findById(roleID)
//                                  .orElseThrow(() -> new ResponseStatusException(
//                                      HttpStatus.NOT_FOUND,
//                                      "Role with ID " + roleID + " not found."
//                                  ));
//
//        // Map CustomerDTO to Customer entity
//        Customer customer = modelMapper.map(customerDTO, Customer.class);
//        customer.setRole(role);
//
//        // Save Customer entity and return mapped CustomerDTO
//        Customer savedCustomer = customerRepository.save(customer);
//        return modelMapper.map(savedCustomer, CustomerDTO.class);
//    }


    public CustomerDTO createCustomer(@Valid CustomerDTO customerDTO) {
        Users user = modelMapper.map(customerDTO.getUser(), Users.class);
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        Users savedUser = userRepo.save(user);
 
        Customer customer = modelMapper.map(customerDTO, Customer.class);
        customer.setUser(savedUser);
 
        Customer savedCustomer = customerRepository.save(customer);
        return modelMapper.map(savedCustomer, CustomerDTO.class);
        }
    
    
    // Update customer
    public CustomerDTO updateCustomer(int customerID,@Valid CustomerDTO updatedCustomerDTO) {
    	Customer customer = customerRepository.findById(customerID).orElse(null);
        if (customer != null) {
            
            modelMapper.map(updatedCustomerDTO, customer);
            customer.setCustomerID(customerID); 
            customerRepository.save(customer);
        }
        return convertToDTO(customer);
    }

    // Delete customer
    public void deleteCustomer(int customerID) {
        customerRepository.deleteById(customerID);
    }
}
