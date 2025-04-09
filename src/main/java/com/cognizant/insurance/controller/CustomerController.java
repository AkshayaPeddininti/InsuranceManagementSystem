package com.cognizant.insurance.controller;
 
import java.util.List;
 
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
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
 
@RestController
 @RequestMapping("/customers")
public class CustomerController {
 
    @Autowired
    CustomerService customerService;
 
    @GetMapping("/getallcustomers")
    public List<CustomerDTO> getAllCustomers() {
        return customerService.getAllCustomers();
    }
 
    @GetMapping("/{customerID}")
    public CustomerDTO getCustomer(@PathVariable int customerID) {
        return customerService.getCustomerById(customerID);
    }
    
    @PostMapping("/add")
    @ResponseStatus(code = HttpStatus.CREATED)
    public void createCustomer(@RequestBody CustomerDTO customerDTO) {
        customerService.createCustomer(customerDTO);
    }
 
    @PutMapping("/update/{customerID}")
    public CustomerDTO updateCustomer(@PathVariable int customerID, @RequestBody CustomerDTO updatedCustomerDTO) {
        return customerService.updateCustomer(customerID, updatedCustomerDTO);
    }
 
    @DeleteMapping("/delete/{customerID}")
    public void removeCustomer(@PathVariable int customerID) {
        customerService.deleteCustomer(customerID);
    }
}