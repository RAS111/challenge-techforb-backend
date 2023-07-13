package com.challenge.techforb.auth.controller;

import com.challenge.techforb.auth.dto.AuthenticationRequest;
import com.challenge.techforb.auth.dto.AuthenticationResponse;
import com.challenge.techforb.auth.dto.ResponseUserDTO;
import com.challenge.techforb.auth.service.UserDetailsCustomService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@CrossOrigin(origins = "*", allowedHeaders = "*")
@RestController
@RequestMapping("/auth")
public class UserAuthController {

    @Autowired
    private UserDetailsCustomService userDetailsServices;

    @PostMapping("/register")
    public ResponseEntity<ResponseUserDTO> signUp(@Valid @RequestBody ResponseUserDTO user) {
        ResponseUserDTO userRegister = this.userDetailsServices.save(user);
        return ResponseEntity.status(HttpStatus.CREATED).body(userRegister);
    }

    @PostMapping("/login")
    public ResponseEntity<AuthenticationResponse> signIn(@RequestBody AuthenticationRequest authenticationRequest) {
        AuthenticationResponse response = userDetailsServices.signIn(authenticationRequest);
        return ResponseEntity.status(HttpStatus.OK).body(response);
    }



}

