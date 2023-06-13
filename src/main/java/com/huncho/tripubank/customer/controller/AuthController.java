package com.huncho.tripubank.customer.controller;

import com.huncho.tripubank.customer.RequestAndResponse.*;
import com.huncho.tripubank.customer.dtos.UserDto;
import com.huncho.tripubank.customer.entity.Account;
import com.huncho.tripubank.customer.exceptions.TripuBankException;
import com.huncho.tripubank.customer.services.AuthService;
import com.huncho.tripubank.customer.services.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("api/v1/auth")
@RequiredArgsConstructor
public class AuthController {

    private final AuthService authService;

    private final UserService userService;

    @PostMapping("/register")
    public ResponseEntity<?> register (@RequestBody CreationRequest creationRequest) throws TripuBankException {
AuthenticationResponse userDto = userService.createUser(creationRequest);


       AuthenticationResponse authenticationResponse = AuthenticationResponse.builder()
               .userDetails(userDto)
               .build();
        return new ResponseEntity<>(authenticationResponse, HttpStatus.OK);


    }
    @PostMapping("/auth")
    public ResponseEntity<AuthenticationResponse> authenticate(@RequestBody AuthenticationRequest authenticationRequest) throws TripuBankException {
        AuthenticationResponse userDto = authService.authenticate(authenticationRequest);
        AuthenticationResponse authenticationResponse = AuthenticationResponse.builder()
                .userDetails(userDto)
                .build();
        return new ResponseEntity<>(authenticationResponse, HttpStatus.OK);
    }
}
