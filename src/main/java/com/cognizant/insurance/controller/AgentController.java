package com.cognizant.insurance.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import java.util.List;
import com.cognizant.insurance.dto.AgentDTO;
import com.cognizant.insurance.dto.CustomerDTO;
import com.cognizant.insurance.dto.PolicyDTO;
import com.cognizant.insurance.dto.ReturnUserDTO;
import com.cognizant.insurance.service.AgentService;

import jakarta.validation.Valid;






import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;



@RestController
@RequestMapping("/agents")
public class AgentController {

@Autowired
AgentService agentService;

@PreAuthorize("#agentId==authentication.principal.id")
@DeleteMapping("/{agentId}")
public ResponseEntity<String> removeAgent(@PathVariable int agentId) {
    ResponseEntity<String> response = new ResponseEntity<>(agentService.removeAgent(agentId), HttpStatus.OK);       
    return response;
}
@PreAuthorize("#agentId==authentication.principal.id")
@GetMapping("/{agentId}")
public ResponseEntity<AgentDTO> viewAgent(@PathVariable int agentId) {
   
    ResponseEntity<AgentDTO> response = new ResponseEntity<>(agentService.viewAgent(agentId), HttpStatus.OK);
   
    return response;
}

//UPDATE agent details
@PutMapping("/update/{agentId}")
public ResponseEntity<String> updateAgent(@PathVariable int agentId,@RequestBody AgentDTO agentDTO) {
    ResponseEntity<String> response = new ResponseEntity<>(agentService.updateAgent(agentId,agentDTO), HttpStatus.CREATED);       
    return response;
}

//getallAgents
@GetMapping("/getAll")
public ResponseEntity<List<ReturnUserDTO>> getAllAgent() {
    
    ResponseEntity<List<ReturnUserDTO>> response = new ResponseEntity<>(agentService.getAllCustomer(), HttpStatus.OK);
   
    return response;
    
}

//add agent
@PostMapping("/add")
public ResponseEntity<AgentDTO> createAgent(@RequestBody AgentDTO agentDTO) {
    ResponseEntity<AgentDTO> response = new ResponseEntity<>(agentService.addAgent(agentDTO), HttpStatus.OK);       
    return response;
}






}
