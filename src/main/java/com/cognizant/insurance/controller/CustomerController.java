package com.cognizant.insurance.controller;
 
import java.util.List;
 
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.web.csrf.CsrfToken;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
 
import com.cognizant.insurance.dto.CustomerDTO;
import com.cognizant.insurance.entity.Customer;
import com.cognizant.insurance.service.CustomerService;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;

@RestController
 @RequestMapping("/customers")
public class CustomerController {
 
    @Autowired
    CustomerService customerService;
 
    @GetMapping("/getallcustomers")
    @PreAuthorize("hasRole('ROLE_CUSTOMER') and #customerID == principal.id")
    public List<CustomerDTO> getAllCustomers() {
        return customerService.getAllCustomers();
    }
 
    @GetMapping("/{customerID}")
    @PreAuthorize("hasRole('ROLE_CUSTOMER') and #customerID == principal.id")
    public CustomerDTO getCustomer(@PathVariable int customerID) {
        return customerService.getCustomerById(customerID);
    }
    

//    @GetMapping("/csrf-token")
//    public CsrfToken getCsrfToken(HttpServletRequest request) {
//    	return (CsrfToken) request.getAttribute(  "_csrf");
//}

    
    @PostMapping("/add")
    @PreAuthorize("hasRole('ROLE_CUSTOMER') and #customerID == principal.id")
    @ResponseStatus(code = HttpStatus.CREATED)
    public void createCustomer(@Valid @RequestBody CustomerDTO customerDTO) {
        customerService.createCustomer(customerDTO);
    }
 
    @PutMapping("/update/{customerID}")
    @PreAuthorize("hasRole('ROLE_CUSTOMER') and #customerID == principal.id")
    public CustomerDTO updateCustomer(@PathVariable int customerID, @Valid @RequestBody CustomerDTO updatedCustomerDTO) {
        return customerService.updateCustomer(customerID, updatedCustomerDTO);
    }
 
    @DeleteMapping("/delete/{customerID}")
    @PreAuthorize("hasRole('ROLE_CUSTOMER') and #customerID == principal.id")
    public void removeCustomer(@PathVariable int customerID) {
        customerService.deleteCustomer(customerID);
    }
}