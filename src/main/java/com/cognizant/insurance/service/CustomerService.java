package com.cognizant.insurance.service;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
import java.util.stream.Collectors;
import com.cognizant.insurance.entity.Customer;
import com.cognizant.insurance.entity.Role;
import com.cognizant.insurance.dto.CustomerDTO;
import com.cognizant.insurance.repository.CustomerRepository;
import com.cognizant.insurance.repository.RoleRepository;

@Service
public class CustomerService {

    @Autowired
    private CustomerRepository customerRepository;
    
    @Autowired
    private RoleRepository roleRepository;

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
    public CustomerDTO createCustomer(CustomerDTO customerDTO) {
        // Validate RoleID
        int roleID = customerDTO.getRole().getRoleID();
        System.out.println("role id.........." + roleID);
        
        if (roleID != 402) {
            throw new ResponseStatusException(
                HttpStatus.BAD_REQUEST,
                "Invalid RoleID. Accepted values are 401 (Agent) and 402 (Customer)."
            );
        }

        // Fetch the Role from the database
        Role role = roleRepository.findById(roleID)
                                  .orElseThrow(() -> new ResponseStatusException(
                                      HttpStatus.NOT_FOUND,
                                      "Role with ID " + roleID + " not found."
                                  ));

        // Map CustomerDTO to Customer entity
        Customer customer = modelMapper.map(customerDTO, Customer.class);
        customer.setRole(role);

        // Save Customer entity and return mapped CustomerDTO
        Customer savedCustomer = customerRepository.save(customer);
        return modelMapper.map(savedCustomer, CustomerDTO.class);
    }


    // Update customer
    public CustomerDTO updateCustomer(int customerID, CustomerDTO updatedCustomerDTO) {
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
