package com.cognizant.insurance.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.cognizant.insurance.dto.LoginRequestDTO;
import com.cognizant.insurance.dto.LoginResponseDTO;
import com.cognizant.insurance.dto.RegisterRequestDTO;
import com.cognizant.insurance.dto.RegisterResponseDTO;
import com.cognizant.insurance.security.UserService;


import jakarta.validation.Valid;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@RestController

public class UserController {

    private static final Logger logger = LoggerFactory.getLogger(UserController.class);

    @Autowired
    UserService userService;

    @PostMapping("/register")
    public ResponseEntity<RegisterResponseDTO> register(@Valid @RequestBody RegisterRequestDTO registerRequestDTO) {
        logger.info("Entering register method with registerRequestDTO: {}", registerRequestDTO);
        ResponseEntity<RegisterResponseDTO> response = new ResponseEntity<>(userService.register(registerRequestDTO), HttpStatus.OK);
        logger.info("Exiting register method with response: {}", response);
        return response;
    }

    @PostMapping("/login")
    public ResponseEntity<LoginResponseDTO> login(@Valid @RequestBody LoginRequestDTO loginRequestDTO) {
        logger.info("Entering login method with loginRequestDTO: {}", loginRequestDTO);
        ResponseEntity<LoginResponseDTO> response = new ResponseEntity<>(userService.login(loginRequestDTO), HttpStatus.OK);
        logger.info("Exiting login method with response: {}", response);
        return response;
    }
}
