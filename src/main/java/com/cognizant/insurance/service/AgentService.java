package com.cognizant.insurance.service;

import org.modelmapper.ModelMapper;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
import java.util.stream.Collectors;
import com.cognizant.insurance.entity.Agent;
import com.cognizant.insurance.entity.Policy;

import com.cognizant.insurance.entity.Users;
import com.cognizant.insurance.dto.AgentDTO;
import com.cognizant.insurance.dto.PolicyDTO;
import com.cognizant.insurance.dto.UserDTO;
import com.cognizant.insurance.repository.AgentRepository;
import com.cognizant.insurance.repository.PolicyRepository;

import com.cognizant.insurance.repository.UserRepo;

import jakarta.validation.Valid;

@Service
public class AgentService {

    @Autowired
    private AgentRepository agentRepository;
    
    @Autowired
    private UserRepo userRepo;
    
    @Autowired
    private PolicyRepository policyRepository;
    @Autowired
    private ModelMapper modelMapper; // Inject ModelMapper bean
    @Autowired
    PasswordEncoder passwordEncoder;

    // Convert from Agent to AgentDTO
    private AgentDTO convertToDTO(Agent agent) {
        return modelMapper.map(agent, AgentDTO.class); // Use ModelMapper
    }
    

    // Convert from AgentDTO to Agent
    private Agent convertToEntity(AgentDTO agentDTO) {
        return modelMapper.map(agentDTO, Agent.class); // Use ModelMapper
    }

    // Get all agents
    public List<AgentDTO> getAllAgents() {
        return agentRepository.findAll()
                              .stream()
                              .map(this::convertToDTO)
                              .collect(Collectors.toList());
    }

    // Get agent by ID
    public AgentDTO getAgentById(int agentID) {
        Agent agent = agentRepository.findById(agentID)
                                     .orElseThrow(() -> new RuntimeException("Agent not found"));
        return convertToDTO(agent);
    }
    
 //create
//    public AgentDTO createAgent(@Valid AgentDTO agentDTO) {
//        // Validate RoleID
////        String role = agentDTO.getUser().getRole();
////        System.out.println("role name"+role);
////        
////        if (role != "ROLE_AGENT") {
////            throw new ResponseStatusException(
////                HttpStatus.BAD_REQUEST,
////                "Invalid Rolename. Accepted values are (ROLE_AGENT) and 402 (ROLE_CUSTOMER)."
////            );
////        }
//
//        // Fetch the User from the database
//        
//       
//        
//        		
//
//        // Map AgentDTO to Agent entity
//        Agent agent = modelMapper.map(agentDTO, Agent.class);
//        agent.setUser(user);
//
//        // Save Agent entity and return mapped AgentDTO
//        Agent savedAgent = agentRepository.save(agent);
//        return modelMapper.map(savedAgent, AgentDTO.class);
//    }
    
    
    
    public AgentDTO createAgent(@Valid AgentDTO agentDTO) {
        Users user = modelMapper.map(agentDTO.getUser(), Users.class);
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        Users savedUser = userRepo.save(user);
 
        Agent agent = modelMapper.map(agentDTO, Agent.class);
        agent.setUser(savedUser);
 
        Agent savedAgent = agentRepository.save(agent);
        return modelMapper.map(savedAgent, AgentDTO.class);
        }
   


    // Update agent
    public AgentDTO updateAgent(int agentID,@Valid AgentDTO updatedAgentDTO) {
        Agent agent = agentRepository.findById(agentID)
                                     .orElseThrow(() -> new RuntimeException("Agent not found"));

        agent.setAgentName(updatedAgentDTO.getAgentName());
        agent.setContactInfo(updatedAgentDTO.getContactInfo());

        Agent updatedAgent = agentRepository.save(agent);
        return convertToDTO(updatedAgent);
    }

    // Delete agent
    public void deleteAgent(int agentID) {
        agentRepository.deleteById(agentID);
    }
    //'''''''''''''''''''''''''''''''''''
    //policy crud operations
    //create policy
    public PolicyDTO createPolicyForAgent(int agentID,@Valid PolicyDTO policyDTO) {
        Agent agent = agentRepository.findById(agentID)
                .orElseThrow(() -> new RuntimeException("Agent not found"));

        Policy policy = modelMapper.map(policyDTO, Policy.class);
        policy.setAgent(agent); // Associate policy with agent

        Policy savedPolicy = policyRepository.save(policy);
        return modelMapper.map(savedPolicy, PolicyDTO.class);
    }
    //getall policies
    public List<PolicyDTO> getPoliciesForAgent(int agentID) {
        Agent agent = agentRepository.findById(agentID)
                .orElseThrow(() -> new RuntimeException("Agent not found"));

        return policyRepository.findById(agent.getAgentID())
                .stream()
                .map(policy -> modelMapper.map(policy, PolicyDTO.class))
                .collect(Collectors.toList());
    }
    //update policy
    public PolicyDTO updatePolicyForAgent(int policyID,@Valid PolicyDTO updatedPolicyDTO) {
        Policy policy = policyRepository.findById(policyID)
                .orElseThrow(() -> new RuntimeException("Policy not found"));

        policy.setPolicyName(updatedPolicyDTO.getPolicyName());
        policy.setPremiumAmount(updatedPolicyDTO.getPremiumAmount());
        policy.setCoverageDetails(updatedPolicyDTO.getCoverageDetails());
        policy.setValidityPeriod(updatedPolicyDTO.getValidityPeriod());

        Policy updatedPolicy = policyRepository.save(policy);
        return modelMapper.map(updatedPolicy, PolicyDTO.class);
    }
    //delete policy
    public void deletePolicyForAgent(int policyID) {
        policyRepository.deleteById(policyID);
    }
}
    
    
    
    
