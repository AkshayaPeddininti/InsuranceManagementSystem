package com.cognizant.insurance.controller;

import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import java.util.List;
import com.cognizant.insurance.dto.AgentDTO;
import com.cognizant.insurance.dto.ClaimDTO;
import com.cognizant.insurance.dto.CustomerDTO;
import com.cognizant.insurance.dto.PolicyDTO;
import com.cognizant.insurance.dto.RegisterRequestDTO;
import com.cognizant.insurance.dto.RegisterResponseDTO;
import com.cognizant.insurance.dto.ReturnUserDTO;
import com.cognizant.insurance.dto.UpdateClaimStatusDTO;
import com.cognizant.insurance.entity.Claim;
import com.cognizant.insurance.security.UserService;
import com.cognizant.insurance.service.AgentService;

import jakarta.validation.Valid;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;





import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;



@RestController
@RequestMapping("/agents")
public class AgentController {

	private static final Logger logger = LoggerFactory.getLogger(AgentController.class);
	@Autowired
	AgentService agentService;
	
	@Autowired
	UserService userService;
	
	@PreAuthorize("#agentId==authentication.principal.id")
	@DeleteMapping("/{agentId}")
	public ResponseEntity<String> removeAgent(@PathVariable int agentId) {
		logger.info("Entering remove Agent method with agentId: {} ", agentId);
	    ResponseEntity<String> response = new ResponseEntity<>(agentService.removeAgent(agentId), HttpStatus.OK);  
	    logger.info("Exiting remove Agent  method with response: {}",response);
	    return response;
	}
	@PreAuthorize("#agentId==authentication.principal.id")
	@GetMapping("/{agentId}")
	public ResponseEntity<AgentDTO> viewAgent(@PathVariable int agentId) {
		logger.info("Entering view Agent method with agentId: {} ",agentId);
	    ResponseEntity<AgentDTO> response = new ResponseEntity<>(agentService.viewAgent(agentId), HttpStatus.OK);
	    logger.info("Exiting view Agent  method with response: {}",response);
	    return response;
	}
	
	//UPDATE agent details
	@PreAuthorize("#agentId==authentication.principal.id")
	@PutMapping("/update/{agentId}")
	public ResponseEntity<String> updateAgent(@PathVariable int agentId,@RequestBody AgentDTO agentDTO) {
		logger.info("Entering update Agent method with agentId: {} and agentDTO: {}", agentId, agentDTO);
	    ResponseEntity<String> response = new ResponseEntity<>(agentService.updateAgent(agentId,agentDTO), HttpStatus.CREATED);       
	    logger.info("Exiting update Agent method with response: {}",response);
	    return response;
	}
	
	//getallAgents
	@GetMapping("/getAll")
	public ResponseEntity<List<ReturnUserDTO>> getAllAgent() {
		logger.info("Entering get All Agent method");
	    ResponseEntity<List<ReturnUserDTO>> response = new ResponseEntity<>(agentService.getAllCustomer(), HttpStatus.OK);
	    logger.info("Exiting get All Agent method with response: {}",response);
	    return response;
	    
	}
	
	//add agent 
	@PostMapping("/add")
	public ResponseEntity<AgentDTO> createAgent(@RequestBody AgentDTO agentDTO) {
	  logger.info("Entering create Agent method with agentId: {} ", agentDTO);
	  ResponseEntity<AgentDTO> response = new ResponseEntity<>(agentService.addAgent(agentDTO), HttpStatus.CREATED); 
	  logger.info("Exiting create Agentt method with response: {}",response);
	  return response;
	}

//.....................policies
//add policy
@PreAuthorize("#agentId==authentication.principal.id")
@PostMapping("/{agentId}/addpolicy")
public ResponseEntity<PolicyDTO> createPolicy(@PathVariable int agentId,@RequestBody PolicyDTO policyDTO) {
	logger.info("Entering create Policy method with agentId: {} and policyDTO: {}", agentId, policyDTO);
  ResponseEntity<PolicyDTO> response = new ResponseEntity<>(agentService.createPolicy(agentId,policyDTO), HttpStatus.OK);       
  logger.info("Exiting  create Policy method with response: {}",response);
  return response;
}




//getbyId
@GetMapping("/{agentId}/policies")
public ResponseEntity<List<PolicyDTO>> viewPolicyById(@PathVariable int agentId) {
	logger.info("Entering get viewPolicyById method with agentId: {}",agentId);
    ResponseEntity<List<PolicyDTO>> response = new ResponseEntity<>(agentService.viewPolicyById(agentId), HttpStatus.OK);
    logger.info("Exiting get viewPolicyById method with response: {}",response);
    return response;
}

@GetMapping("/claims/status/{status}")
public ResponseEntity<List<Claim>> getAllClaimsByStatus(@PathVariable String status) {
	logger.info("Entering getAllClaimsByStatus method by userId");
    List<Claim> claims = agentService.getAllClaimsByStatus(status);
    logger.info("Exiting  getAllClaimsByStatus method");
    return ResponseEntity.ok(claims);
}

//.........................CLAIMS


    @PatchMapping("/{claimId}/updateStatus")
    public ResponseEntity<Claim> updateClaimStatus(@PathVariable int claimId, @RequestBody UpdateClaimStatusDTO updateClaimStatusDTO) {
    	logger.info("Entering updateClaimStatus method by claimId",claimId);
        try {
            Claim updatedClaim = agentService.updateClaimStatus(claimId, updateClaimStatusDTO);
            logger.info("Exiting  updateClaimStatus method in try block");
            return ResponseEntity.ok(updatedClaim);
        } catch (RuntimeException e) {
        	  logger.info("Exiting  updateClaimStatus method in catch block");
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
        }
        
    }



}





