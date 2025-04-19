package com.cognizant.insurance.service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import com.cognizant.insurance.dto.AgentDTO;
import com.cognizant.insurance.dto.CustomerDTO;
import com.cognizant.insurance.dto.ReturnUserDTO;
import com.cognizant.insurance.entity.Agent;
import com.cognizant.insurance.entity.Customer;
import com.cognizant.insurance.exception.AllException.AgentDetailNotFound;
import com.cognizant.insurance.exception.AllException.CustomerDetailNotFound;
import com.cognizant.insurance.repository.AgentRepository;
import com.cognizant.insurance.repository.CustomerRepository;



@Service
public class AgentService {
@Autowired
	ModelMapper modelMapper;
@Autowired
AgentRepository agentRepository ;

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
	
}

