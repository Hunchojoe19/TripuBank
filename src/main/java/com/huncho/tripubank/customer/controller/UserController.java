package com.huncho.tripubank.customer.controller;

import com.huncho.tripubank.customer.RequestAndResponse.BankResponse;
import com.huncho.tripubank.customer.RequestAndResponse.CreationRequest;
import com.huncho.tripubank.customer.RequestAndResponse.UpdateProfileRequest;
import com.huncho.tripubank.customer.dtos.UserDto;
import com.huncho.tripubank.customer.entity.Account;
import com.huncho.tripubank.customer.entity.User;
import com.huncho.tripubank.customer.exceptions.TripuBankException;
import com.huncho.tripubank.customer.services.UserService;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("api/v1/users")
@CrossOrigin(origins = "http://localhost:9700")
@Slf4j
public class UserController {

    @Autowired
    UserService userService;


    @GetMapping("/get/{id}")
    ResponseEntity <?> getAUser(@PathVariable long id) throws TripuBankException {
        try{
           if (("null".equals(id)) || ("").equals(String.valueOf(id).trim())){
               throw new TripuBankException("Id cannot be an empty string", 400);
           }
            UserDto userDto = userService.findUserById(id);
            BankResponse bankResponse = BankResponse.builder()
                    .responseCode("200")
                    .responseMessage("User found")
                    .data(userDto)
                    .accountInfo(Account.builder()
                            .accountName(userDto.getFirstName()+ " "+userDto.getLastName())
                            .accountNumber(userDto.getAccount().getAccountNumber())
                            .accountBalance(userDto.getAccount().getAccountBalance())
                            .build())
                    .build();
             return new ResponseEntity<>(bankResponse, HttpStatus.OK);
        }
        catch (TripuBankException e){
            BankResponse bankResponse = BankResponse.builder()
                    .responseCode("400")
                    .responseMessage("User not found")
                    .accountInfo(null)
                    .build();
            return new ResponseEntity<>(bankResponse, HttpStatusCode.valueOf(e.getStatusCode()));
        }

    }
    @GetMapping("all")
    public List<UserDto> getAllUser() {
        return userService.findAllUsers();
    }
    @GetMapping("/account_number/{accountNumber}")
    public ResponseEntity <?> getUserByAza(@PathVariable("accountNumber") String accountNumber) throws TripuBankException {
        UserDto userDto = userService.findUserByAccountNumber(accountNumber);
        BankResponse bankResponse = BankResponse.builder()
                .responseCode("200")
                .responseMessage("User found")
                .data(userDto)
                .accountInfo(Account.builder()
                        .accountName(userDto.getFirstName()+ " "+userDto.getLastName())
                        .accountNumber(userDto.getAccount().getAccountNumber())
                        .accountBalance(userDto.getAccount().getAccountBalance())
                        .build())
                .build();
        return new ResponseEntity<>(bankResponse, HttpStatus.OK);
    }
    @PatchMapping("update/{id}")
    ResponseEntity <?> updateUser(@PathVariable @Valid long id, @Valid @NotBlank@NotNull @RequestBody UpdateProfileRequest updateProfileRequest) throws TripuBankException {
       try {
           UserDto userDto = userService.updateUserProfile(id, updateProfileRequest);
           BankResponse bankResponse = BankResponse.builder()
                   .responseCode("201")
                   .responseMessage("Update Successful")
                   .data(userDto)
                   .accountInfo(userDto.getAccount())
                   .build();
           return new ResponseEntity<>(bankResponse, HttpStatus.CREATED);
       }
       catch (TripuBankException e){
           BankResponse bankResponse = BankResponse.builder()
                   .responseCode("400")
                   .responseMessage("Update Unsuccessful")
                   .data(null)
                   .build();
           return new ResponseEntity<>(bankResponse, HttpStatus.valueOf(e.getStatusCode()));
       }
    }
    @PutMapping("/{id}")
    ResponseEntity <?> updateAllFields (@PathVariable("id") long id, @RequestBody @Valid UpdateProfileRequest updateProfileRequest){
        try {
            UserDto userDto = userService.updateFewFields(id, updateProfileRequest);
            BankResponse bankResponse = BankResponse.builder()
                    .responseCode("201")
                    .responseMessage("Update Successful")
                    .data(userDto)
                    .accountInfo(Account.builder()
                            .accountName(userDto.getFirstName()+" "+ userDto.getLastName())
                            .accountNumber(userDto.getAccount().getAccountNumber())
                            .accountBalance(userDto.getAccount().getAccountBalance())
                            .build())
                    .build();
            return new ResponseEntity<>(bankResponse, HttpStatus.CREATED);
        }
        catch (TripuBankException e){
            BankResponse bankResponse = BankResponse.builder()
                    .responseCode("401")
                    .responseMessage("Update Failed")
                    .data(null)
                    .build();
            return new ResponseEntity<>(bankResponse, HttpStatus.valueOf(e.getStatusCode()));
        }
    }
    @DeleteMapping("/delete/{id}")
    ResponseEntity <?> deleteUser(@PathVariable("id") long id) throws TripuBankException{
        userService.deleteAUser(id);
        BankResponse bankResponse = BankResponse.builder()
                .data(null)
                .accountInfo(null)
                .build();
        return new ResponseEntity<>(bankResponse, HttpStatus.OK);
    }

}
