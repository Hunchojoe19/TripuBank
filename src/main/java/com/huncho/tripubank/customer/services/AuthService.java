package com.huncho.tripubank.customer.services;

import com.huncho.tripubank.customer.RequestAndResponse.AuthenticationRequest;
import com.huncho.tripubank.customer.RequestAndResponse.AuthenticationResponse;
import com.huncho.tripubank.customer.RequestAndResponse.CreationRequest;
import com.huncho.tripubank.customer.entity.User;
import com.huncho.tripubank.customer.entity.enumeration.Role;
import com.huncho.tripubank.customer.exceptions.TripuBankException;
import com.huncho.tripubank.customer.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AuthService {

    private final UserRepository userRepository;

    private final PasswordEncoder passwordEncoder;

    private final JwtService jwtService;

    private final AuthenticationManager authenticationManager;

    public AuthenticationResponse register(CreationRequest registerRequest) {
        var user = User.builder()
                .firstName(registerRequest.getFirstName())
                .lastName(registerRequest.getLastName())
                .email(registerRequest.getEmail())
                .password(passwordEncoder.encode(registerRequest.getPassword()))
                .role(Role.USER)
                .build();

        userRepository.save(user);
        var jwtToken = jwtService.generateToken(user);
        return AuthenticationResponse.builder()
               .token(jwtToken)
                .userDetails(user)
                .build();
    }

    public AuthenticationResponse authenticate(AuthenticationRequest authenticationRequest) throws TripuBankException {
        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(authenticationRequest.getEmail(), authenticationRequest.getPassword())
        );
        var user = userRepository.findUserByEmail(authenticationRequest.getEmail()).orElseThrow(()-> new TripuBankException("User not found", 400));
        var jwtToken = jwtService.generateToken(user);
        return AuthenticationResponse.builder()
               .token(jwtToken)
                .userDetails(user)
                .statusCode(200)
                .build();
    }
}
