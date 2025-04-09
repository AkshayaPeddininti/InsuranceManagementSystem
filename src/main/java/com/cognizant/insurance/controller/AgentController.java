package com.cognizant.insurance.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import java.util.List;
import com.cognizant.insurance.dto.AgentDTO;
import com.cognizant.insurance.dto.PolicyDTO;
import com.cognizant.insurance.service.AgentService;

@RestController
@RequestMapping("/agents")
public class AgentController {

    @Autowired
    private AgentService agentService; 
    // Service layer dependency
   
 // Create agent
    @PostMapping("/add")
    public AgentDTO createAgent(@RequestBody AgentDTO agentDTO) {
    	
        return agentService.createAgent(agentDTO); // Creates a new agent
    }
    // Get all agents
    @GetMapping("/getall")
    public List<AgentDTO> getAllAgents() {
        return agentService.getAllAgents(); // Returns a list of AgentDTO
    }

    // Get agent by ID
    @GetMapping("/{agentID}")
    public AgentDTO getAgentById(@PathVariable int agentID) {
        return agentService.getAgentById(agentID); // Fetches an agent by its ID
    }

    

    // Update agent
    @PutMapping("/{agentID}")
    public AgentDTO updateAgent(@PathVariable int agentID, @RequestBody AgentDTO agentDTO) {
        return agentService.updateAgent(agentID, agentDTO); // Updates an existing agent
    }

    // Delete agent
    @DeleteMapping("/{agentID}")
    public void deleteAgent(@PathVariable int agentID) {
        agentService.deleteAgent(agentID); // Deletes an agent by ID
    }
    @PostMapping("/{agentID}/policies")
    public PolicyDTO createPolicyForAgent(@PathVariable int agentID, @RequestBody PolicyDTO policyDTO) {
        return agentService.createPolicyForAgent(agentID, policyDTO);
    }
    @GetMapping("/{agentID}/policies")
    public List<PolicyDTO> getPoliciesForAgent(@PathVariable int agentID) {
        return agentService.getPoliciesForAgent(agentID);
    }
    @PutMapping("/policies/{policyID}")
    public PolicyDTO updatePolicyForAgent(@PathVariable int policyID, @RequestBody PolicyDTO updatedPolicyDTO) {
        return agentService.updatePolicyForAgent(policyID, updatedPolicyDTO);
    }
    @DeleteMapping("/policies/{policyID}")
    public void deletePolicyForAgent(@PathVariable int policyID) {
        agentService.deletePolicyForAgent(policyID);
    }
    
}
