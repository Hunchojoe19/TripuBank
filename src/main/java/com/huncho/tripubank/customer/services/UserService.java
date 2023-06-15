package com.huncho.tripubank.customer.services;

import com.huncho.tripubank.customer.RequestAndResponse.AuthenticationResponse;
import com.huncho.tripubank.customer.RequestAndResponse.BankResponse;
import com.huncho.tripubank.customer.RequestAndResponse.CreationRequest;
import com.huncho.tripubank.customer.RequestAndResponse.UpdateProfileRequest;
import com.huncho.tripubank.customer.dtos.UserDto;
import com.huncho.tripubank.customer.exceptions.TripuBankException;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface UserService {
    AuthenticationResponse createUser(CreationRequest creationRequest) throws TripuBankException;
    UserDto findUserById(long id) throws TripuBankException;
    List<UserDto> findAllUsers();

    UserDto findUserByAccountNumber(String accountNumber) throws TripuBankException;

UserDto updateUserProfile( long id, UpdateProfileRequest updateProfileRequest) throws TripuBankException;
    UserDto updateFewFields (long id, UpdateProfileRequest updateProfileRequest) throws TripuBankException;

    BankResponse deleteAUser(long id) throws TripuBankException;
}
