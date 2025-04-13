package com.cognizant.insurance.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import java.util.List;
import com.cognizant.insurance.dto.AgentDTO;
import com.cognizant.insurance.dto.PolicyDTO;
import com.cognizant.insurance.service.AgentService;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/agents")
public class AgentController {

    @Autowired
    private AgentService agentService; 
    // Service layer dependency
   
 // Create agent
     @PreAuthorize("hasRole('ROLE_AGENT') and #agentID == principal.id")
    @PostMapping("/add")
    public AgentDTO createAgent(@Valid @RequestBody AgentDTO agentDTO) {
    	
        return agentService.createAgent(agentDTO); // Creates a new agent
    }
    // Get all agents
     @PreAuthorize("hasRole('ROLE_AGENT') and #agentID == principal.id")
    @GetMapping("/getall")
    public List<AgentDTO> getAllAgents() {
        return agentService.getAllAgents(); // Returns a list of AgentDTO
    }

    // Get agent by ID
     @PreAuthorize("hasRole('ROLE_AGENT') and #agentID == principal.id")
    @GetMapping("/{agentID}")
    public AgentDTO getAgentById(@PathVariable int agentID) {
        return agentService.getAgentById(agentID); // Fetches an agent by its ID
    }

    

    // Update agent
     @PreAuthorize("hasRole('ROLE_AGENT') and #agentID == principal.id")
    @PutMapping("/update/{agentID}")
    public AgentDTO updateAgent(@PathVariable int agentID,@Valid @RequestBody AgentDTO agentDTO) {
        return agentService.updateAgent(agentID, agentDTO); // Updates an existing agent
    }

    // Delete agent
     @PreAuthorize("hasRole('ROLE_AGENT') and #agentID == principal.id")
    @DeleteMapping("/delete/{agentID}")
    public void deleteAgent(@PathVariable int agentID) {
        agentService.deleteAgent(agentID); // Deletes an agent by ID
    }
    @PostMapping("/{agentID}/policies")
  
    public PolicyDTO createPolicyForAgent(@PathVariable int agentID,@Valid  @RequestBody PolicyDTO policyDTO) {
        return agentService.createPolicyForAgent(agentID, policyDTO);
    }
    @GetMapping("/{agentID}/policies")
    public List<PolicyDTO> getPoliciesForAgent(@PathVariable int agentID) {
        return agentService.getPoliciesForAgent(agentID);
    }
    @PutMapping("/policies/{policyID}")
    public PolicyDTO updatePolicyForAgent(@PathVariable int policyID,@Valid @RequestBody PolicyDTO updatedPolicyDTO) {
        return agentService.updatePolicyForAgent(policyID, updatedPolicyDTO);
    }
    @DeleteMapping("/policies/{policyID}")
    public void deletePolicyForAgent(@PathVariable int policyID) {
        agentService.deletePolicyForAgent(policyID);
    }
    
}
