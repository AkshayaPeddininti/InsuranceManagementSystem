package com.cognizant.insurance.controller;
 
import java.lang.System.Logger;
import java.util.List;
 
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.web.csrf.CsrfToken;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.cognizant.insurance.dto.AgentDTO;
import com.cognizant.insurance.dto.CustomerDTO;
import com.cognizant.insurance.dto.ReturnUserDTO;
import com.cognizant.insurance.dto.UserDTO;
import com.cognizant.insurance.entity.Customer;
import com.cognizant.insurance.service.CustomerService;


import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;

@RestController
@RequestMapping("/customers")
public class CustomerController {
 
    @Autowired
    CustomerService customerService;
 
    @GetMapping("/getAll")
    public ResponseEntity<List<ReturnUserDTO>> getALlCustomer() {
        
        ResponseEntity<List<ReturnUserDTO>> response = new ResponseEntity<>(customerService.getAllCustomer(), HttpStatus.OK);
       
        return response;
    }

    @PreAuthorize("#customerId==authentication.principal.id")
    @DeleteMapping("/{customerId}")
    public ResponseEntity<String> removeCustomer(@PathVariable int customerId) {
        ResponseEntity<String> response = new ResponseEntity<>(customerService.removeCustomer(customerId), HttpStatus.OK);       
        return response;
    }
    
    @PutMapping("/update/{customerID}")
    public ResponseEntity<String> updateCustomer(@PathVariable int customerID,@RequestBody CustomerDTO customerDTO) {
        ResponseEntity<String> response = new ResponseEntity<>(customerService.updateCustomer(customerID,customerDTO), HttpStatus.CREATED);       
        return response;
    }
    
    @PreAuthorize("#customerId==authentication.principal.id")
    @GetMapping("/{customerId}")
    public ResponseEntity<CustomerDTO> viewCustomer(@PathVariable int customerId) {
       
        ResponseEntity<CustomerDTO> response = new ResponseEntity<>(customerService.viewCustomer(customerId), HttpStatus.OK);
       
        return response;
    }
    
//  
////add customer
//  @PostMapping("/add")
//  public ResponseEntity<CustomerDTO> createAgent(@RequestBody CustomerDTO customerDTO) {
//      ResponseEntity<CustomerDTO> response = new ResponseEntity<>(customerService.addCustomer(customerDTO), HttpStatus.CREATED);       
//      return response;
//  }
    
    
    

}