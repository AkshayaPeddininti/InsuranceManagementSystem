package com.cognizant.insurance.controller;
 

import org.slf4j.Logger;

import org.slf4j.LoggerFactory;

import java.util.List;
import java.util.Map;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.web.csrf.CsrfToken;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.cognizant.insurance.dto.AgentDTO;
import com.cognizant.insurance.dto.ClaimDTO;
import com.cognizant.insurance.dto.CustomerDTO;
import com.cognizant.insurance.dto.PolicyDTO;
import com.cognizant.insurance.dto.ReturnUserDTO;
import com.cognizant.insurance.dto.UserDTO;
import com.cognizant.insurance.dto.ViewPoliciesDTO;
import com.cognizant.insurance.entity.Claim;
import com.cognizant.insurance.entity.Customer;
import com.cognizant.insurance.service.AgentService;
import com.cognizant.insurance.service.CustomerService;


import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;

@RestController
@RequestMapping("/customers")
public class CustomerController {
 
	private static final Logger logger = LoggerFactory.getLogger(CustomerController.class);
	
    @Autowired
    CustomerService customerService;
    

 
    @GetMapping("/getAll")
    public ResponseEntity<List<ReturnUserDTO>> getALlCustomer() {
    	logger.info("Entering get all Customer method");
        ResponseEntity<List<ReturnUserDTO>> response = new ResponseEntity<>(customerService.getAllCustomer(), HttpStatus.OK);
        logger.info("Exiting get all Customer method");
        return response;
    }

    @PreAuthorize("#customerId==authentication.principal.id")
    @DeleteMapping("/{customerId}")
    public ResponseEntity<String> removeCustomer(@PathVariable int customerId) {
    	logger.info("Entering remove Customer method with customerID: {} ", customerId);
        ResponseEntity<String> response = new ResponseEntity<>(customerService.removeCustomer(customerId), HttpStatus.OK);       
        logger.info("Exiting remove Customer method with response {} ", response);
        return response;
    }
    
    @PutMapping("/update/{customerID}")
    public ResponseEntity<String> updateCustomer(@PathVariable int customerID,@RequestBody CustomerDTO customerDTO) {
    	logger.info("Entering update Customer method with customerID: {} and customerDTO {} ", customerID,customerDTO);
        ResponseEntity<String> response = new ResponseEntity<>(customerService.updateCustomer(customerID,customerDTO), HttpStatus.CREATED); 
        logger.info("Exiting update Customer method with response {} ", response);
        return response;
    }
    
    @PreAuthorize("#customerId==authentication.principal.id")
    @GetMapping("/{customerId}")
    public ResponseEntity<CustomerDTO> viewCustomer(@PathVariable int customerId) {
    	logger.info("Entering view Customer method with customerID: {} ", customerId);
        ResponseEntity<CustomerDTO> response = new ResponseEntity<>(customerService.viewCustomer(customerId), HttpStatus.OK);
        logger.info("Exiting view Customer method with response: {} ", response);
        return response;
    }
    
    
  //getallPolicies
    @GetMapping("/getAllPolicies")
    public ResponseEntity<List<ViewPoliciesDTO>> getAllPolicies() {
    	logger.info("Entering get All Policies method");
      ResponseEntity<List<ViewPoliciesDTO>> response = new ResponseEntity<>(customerService.getAllPolicies(), HttpStatus.OK);
      logger.info("Exiting get AllPolicies method with response: {}",response);
      return response;
      
    }
    //.....policy
    @PreAuthorize("#userId==authentication.principal.id")
     @PatchMapping("/{userId}/applyPolicy/{policyId}")
            public ResponseEntity<PolicyDTO> applyPolicy(@PathVariable int userId, @PathVariable int policyId) {
    	 logger.info("Entering applyPolicy method with customerID: {} AND policyId :{} ", userId,policyId);      
    	 PolicyDTO updatedPolicy = customerService.applyPolicy(userId, policyId);
    	 logger.info("Exiting applyPolicy method");  
                return ResponseEntity.ok(updatedPolicy);
            }
            
     //...............CLAIM
    			@PreAuthorize("#userId==authentication.principal.id")
                @PatchMapping("/{userId}/fileClaim")
                public ResponseEntity<ClaimDTO> fileClaim(@PathVariable int userId, @RequestBody ClaimDTO claimDTO) {
                    logger.info("Entering file a claim method with customerID: {} ", userId);
                    ClaimDTO filedClaim = customerService.fileClaim(userId, claimDTO);
                    logger.info("Exiting file a claim method with customerID: {} ", userId);
                    return ResponseEntity.ok(filedClaim);
                }


        
    			@PreAuthorize("#customerID==authentication.principal.id")
    			@GetMapping("/claims/user/{customerID}")
    			public ResponseEntity<Optional<List<Claim>>> getClaimsByUserId(@PathVariable int customerID) {
    				logger.info("Entering getClaimsByUserId method by userId",customerID);
    			    Optional<List<Claim>> claims = customerService.getClaimsByUserId(customerID);
    			    logger.info("Exiting  getClaimsByUserId method");
    			    return ResponseEntity.ok(claims);
    			}

  
//add customer
  @PostMapping("/add")
  public ResponseEntity<CustomerDTO> createAgent(@RequestBody CustomerDTO customerDTO) {
	  logger.info("Entering  createAgent method with customerDTO: {} ", customerDTO);
      ResponseEntity<CustomerDTO> response = new ResponseEntity<>(customerService.addCustomer(customerDTO), HttpStatus.CREATED);       
      logger.info("Exiting createAgent method with response: {} ", response);
      return response;
  }
    
    
    

}