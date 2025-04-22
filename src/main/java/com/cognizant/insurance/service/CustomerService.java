package com.cognizant.insurance.service;

import java.util.HashSet;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import com.cognizant.insurance.dto.ClaimDTO;
import com.cognizant.insurance.dto.CustomerDTO;
import com.cognizant.insurance.dto.PolicyDTO;
import com.cognizant.insurance.dto.ReturnUserDTO;
import com.cognizant.insurance.dto.UserDTO;
import com.cognizant.insurance.dto.ViewPoliciesDTO;
import com.cognizant.insurance.entity.Claim;
import com.cognizant.insurance.entity.Customer;
import com.cognizant.insurance.entity.Policy;
import com.cognizant.insurance.exception.AllException.ClaimAmountError;
import com.cognizant.insurance.exception.AllException.CustomerDetailNotFound;
import com.cognizant.insurance.repository.ClaimRepository;
import com.cognizant.insurance.repository.CustomerRepository;
import com.cognizant.insurance.repository.PolicyRepository;

@Service
public class CustomerService {
	@Autowired
	ModelMapper modelMapper;
	@Autowired
	CustomerRepository customerRepository;
	@Autowired
	PolicyRepository policyRepository;
	
	@Autowired
	ClaimRepository claimRepository;

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
	
//get policies
	public List<ViewPoliciesDTO> getAllPolicies() {
		List<Policy> policies=policyRepository.findAll();
		return policies.stream()
                .map(policy -> modelMapper.map(policy, ViewPoliciesDTO.class))
                .collect(Collectors.toList());
	}
//APPLY POLICY
	   
	        public PolicyDTO applyPolicy(int userId, int policyId) {
	            Policy policy = policyRepository.findById(policyId)
	                    .orElseThrow(() -> new CustomerDetailNotFound("Policy with Id " + policyId + " not found."));
	            
	            Customer customer = customerRepository.findById(userId)
	                    .orElseThrow(() -> new CustomerDetailNotFound("Customer with Id " + userId + " not found."));

	            if (policy.getCustomer() == null) {
	                policy.setCustomer(new HashSet<>());
	            }
	            policy.getCustomer().add(customer);
	            policy = policyRepository.save(policy);

	            return modelMapper.map(policy, PolicyDTO.class);
	        }
	        
	       


	     // file a claim
	        public ClaimDTO fileClaim(int userId, ClaimDTO claimDTO) {
	            Policy policy = policyRepository.findById(claimDTO.getPolicyID())
	                    .orElseThrow(() -> new CustomerDetailNotFound("Policy with Id " + claimDTO.getPolicyID() + " not found."));
	            
	            Customer customer = customerRepository.findById(userId)
	                    .orElseThrow(() -> new CustomerDetailNotFound("Customer with Id " + userId + " not found."));
	            float claimAmount=claimDTO.getClaimAmount();
	            float premiumAmount=policy.getPremiumAmount();
	            if(claimAmount>premiumAmount)
	            {
	            	throw new ClaimAmountError("Claim amount should be less than Premium amount");
	            }
	            Claim claim = modelMapper.map(claimDTO, Claim.class);
	            claim.setPolicy(policy);
	            claim.setCustomer(customer);
	            claim.setStatus("Pending"); // Set status to "Pending" by default
	            claim = claimRepository.save(claim);

	            return modelMapper.map(claim, ClaimDTO.class);
	        }
	   	 public Optional<List<Claim>> getClaimsByUserId(int customerID) {
			    return claimRepository.findByCustomerUserId(customerID);
			}

	        }

	    

	    
	

	
	
	


