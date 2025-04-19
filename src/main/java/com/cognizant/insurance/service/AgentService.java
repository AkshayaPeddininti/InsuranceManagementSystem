package com.cognizant.insurance.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.apache.catalina.User;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import com.cognizant.insurance.dto.AgentDTO;
import com.cognizant.insurance.dto.CustomerDTO;
import com.cognizant.insurance.dto.PolicyDTO;
import com.cognizant.insurance.dto.ReturnUserDTO;
import com.cognizant.insurance.entity.Agent;
import com.cognizant.insurance.entity.Customer;
import com.cognizant.insurance.entity.Policy;
import com.cognizant.insurance.entity.Users;
import com.cognizant.insurance.exception.AllException.AgentDetailNotFound;
import com.cognizant.insurance.exception.AllException.CustomerDetailNotFound;
import com.cognizant.insurance.repository.AgentRepository;
import com.cognizant.insurance.repository.CustomerRepository;
import com.cognizant.insurance.repository.PolicyRepository;
import com.cognizant.insurance.repository.UserRepo;

import jakarta.validation.Valid;



@Service
public class AgentService {
@Autowired
	ModelMapper modelMapper;
@Autowired
AgentRepository agentRepository ;

@Autowired
PolicyRepository policyRepository ;
@Autowired
UserRepo userRepository;


BCryptPasswordEncoder encoder=new BCryptPasswordEncoder(12);

	public AgentDTO addAgent(AgentDTO agentDTO){
		
		Agent agent= modelMapper.map(agentDTO,Agent.class);
		agent.setPassword(encoder.encode(agent.getPassword()));
		agent=agentRepository.save(agent);
		
		return modelMapper.map(agent, AgentDTO.class);
	}
	
	
	public String removeAgent(int agentId) {
	Optional<Agent> container=agentRepository.findById(agentId);
	if(!container.isPresent()) {
		return "failed no one";
	}
	Agent agent=container.get();
	agentRepository.delete(agent);
	return "successful";
	
	}
	
	
	public AgentDTO viewAgent(int agentId) {
Agent agent=agentRepository.findById(agentId).orElseThrow(
	()->new AgentDetailNotFound("Agent with Id "+agentId+" not found."));
		return modelMapper.map(agent,AgentDTO.class);
	}


	public String updateAgent(int agentId, AgentDTO agentDTO) {
		// TODO Auto-generated method stub
		
		Agent agent = agentRepository.findById(agentId)
				.orElseThrow(() -> new CustomerDetailNotFound("Agent with Id " + agentId + " not found."));
	
		if(agentDTO.getName()!=null)agent.setName(agentDTO.getName());
		if(agentDTO.getEmail()!=null)agent.setEmail(agentDTO.getEmail());
		if(agentDTO.getPhno()!=null)agent.setPhno(agentDTO.getPhno());
		if(agentDTO.getPassword()!=null)agent.setPassword(encoder.encode(agentDTO.getPassword()));
		agentRepository.save(agent);
		return "Created";
	}
	
	public List<ReturnUserDTO> getAllCustomer() {
		// TODO Auto-generated method stub
		List<Agent> agents=agentRepository.findAll();
		return agents.stream()
                .map(agent -> modelMapper.map(agent, ReturnUserDTO.class))
                .collect(Collectors.toList());
	}

	//..................Policies
	
//create policy
	public PolicyDTO createPolicy(int agentId,PolicyDTO policyDTO) {
		Policy policy= modelMapper.map(policyDTO,Policy.class);
		Agent agent=agentRepository.findById(agentId)
				.orElseThrow(()->new AgentDetailNotFound("Agent with Id "+agentId+" not found."));
		policy.setAgent(agent);
		policy=policyRepository.save(policy);
		
		return modelMapper.map(policy, PolicyDTO.class);
	}


	public List<PolicyDTO> getAllPolicies() {
		List<Policy> policies=policyRepository.findAll();
		return policies.stream()
                .map(policy -> modelMapper.map(policy, PolicyDTO.class))
                .collect(Collectors.toList());
	}

	 public List<PolicyDTO> viewPolicyById(int agentID) {
	        Users agent = userRepository.findById(agentID)
	                .orElseThrow(() -> new RuntimeException("Agent not found"));

	        return policyRepository.findById(agent.getUserId())
	                .stream()
	                .map(policy -> modelMapper.map(policy, PolicyDTO.class))
	                .collect(Collectors.toList());
	    }
	
//	   //update policy
//	    public PolicyDTO updatePolicy(int policyID,PolicyDTO updatedPolicyDTO) {
//	        Policy policy = policyRepository.findById(policyID)
//	                .orElseThrow(() -> new RuntimeException("Policy not found"));
//
//	        policy.setPolicyName(updatedPolicyDTO.getPolicyName());
//	        policy.setPremiumAmount(updatedPolicyDTO.getPremiumAmount());
//	        policy.setCoverageDetails(updatedPolicyDTO.getCoverageDetails());
//	        policy.setValidityPeriod(updatedPolicyDTO.getValidityPeriod());
//
//	        Policy updatedPolicy = policyRepository.save(policy);
//	        return modelMapper.map(updatedPolicy, PolicyDTO.class);
//	    }
//	    //delete policy
//	    public void deletePolicyForAgent(int policyID) {
//	        policyRepository.deleteById(policyID);
//	    }
//	 
	 
//	public List<PolicyDTO> viewPolicyById(int agentId) {
//		Agent agent = agentRepository.findById(agentId)
//				.orElseThrow(() -> new AgentDetailNotFound("Agent with Id " + agentId + " not found."));
//		
//		List<Policy> courseList=policyRepository.findByAgentId(agent);
//		ArrayList<PolicyDTO> ret=new ArrayList<>();
//		for(Policy obj:courseList) {
//			ret.add(modelMapper.map(obj, PolicyDTO.class));
//		}
//		return ret;
////		return modelMapper.map(policy, PolicyDTO.class);
//	}

	
	
}

