package com.huncho.tripubank.customer.services;

import com.huncho.tripubank.customer.RequestAndResponse.AuthenticationResponse;
import com.huncho.tripubank.customer.RequestAndResponse.CreationRequest;
import com.huncho.tripubank.customer.RequestAndResponse.UpdateProfileRequest;
import com.huncho.tripubank.customer.dtos.EmailDetails;
import com.huncho.tripubank.customer.dtos.UserDto;
import com.huncho.tripubank.customer.entity.User;
import com.huncho.tripubank.customer.entity.enumeration.Role;
import com.huncho.tripubank.customer.exceptions.TripuBankException;
import com.huncho.tripubank.customer.repository.UserRepository;
import com.huncho.tripubank.customer.utils.AccountNumberGenerator;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Objects;
import java.util.Optional;


@Service
@Slf4j
public class UserServiceImpl implements UserService{

    @Autowired
    UserRepository userRepository;

    @Autowired
    EmailService emailService;

    @Autowired
    ModelMapper modelMapper;
    @Autowired
    JwtService jwtService;

    @Override
    public AuthenticationResponse createUser(CreationRequest creationRequest) throws TripuBankException {
        if(userRepository.existsUserByEmail(creationRequest.getEmail())){
            throw new TripuBankException("Email already exists ", 401);
        }
        User user = User.builder()
                .firstName(creationRequest.getFirstName())
                .lastName(creationRequest.getLastName())
                .email(creationRequest.getEmail())
                .address(creationRequest.getAddress())
//                .password(bCryptPasswordEncoder.encode(creationRequest.getPassword()))
                .password(creationRequest.getPassword())
                .phone(creationRequest.getPhone())
                .gender(creationRequest.getGender())
                .role(Role.USER)
                .status(creationRequest.getStatus())
                        .accountBalance(BigDecimal.ZERO)
                        .accountName(creationRequest.getFirstName()+ " "+ creationRequest.getLastName())
                        .accountNumber(AccountNumberGenerator.accountNumberGenerator())
                .build();
        User savedUser = userRepository.save(user);

        EmailDetails emailDetails = EmailDetails.builder()
                .recipient(savedUser.getEmail())
                .subject("Creation Of Account")
                .messageBody("Congratulation!!! on Creating Your Account With 3puBank. \n\n\nYour Account Details: \n"+
                "Account Name: "+ savedUser.getAccountName()+ " \n"+
                "Account Number: "+ savedUser.getAccountNumber()+"\n"+
                "Account Balance: "+ "₦ "+savedUser.getAccountBalance())
                .build();
        emailService.sendEmailAlerts(emailDetails);

        var jwtToken = jwtService.generateToken(user);
        return AuthenticationResponse.builder()
               .token(jwtToken)
                .userDetails(user)
               .statusCode(200)
                .build();

    }

    @Override
    public UserDto findUserById(long id) throws TripuBankException {
      User user = userRepository.findById(id).orElseThrow(()-> new TripuBankException("User Not Found", 404));
      return modelMapper.map(user, UserDto.class);
    }

    @Override
    public List<UserDto> findAllUsers() {
      return userRepository.findAll().stream().map(user -> modelMapper.map(user, UserDto.class)).toList();

    }

    @Override
    public UserDto findUserByAccountNumber(String accountNumber) throws TripuBankException {
        User user = userRepository.findUserByAccountNumber(accountNumber).orElseThrow(() -> new TripuBankException("No user found by this account number", 404));
        return modelMapper.map(user, UserDto.class);
    }
    @Override
    public UserDto updateUserProfile( long id, UpdateProfileRequest updateProfileRequest) throws TripuBankException {
       User userToSave = userRepository.findById(id).orElseThrow(()-> new TripuBankException("User not found", 404));
        User user = modelMapper.map(updateProfileRequest, User.class);
        if(userToSave.getId() != null){
        user.setId(userToSave.getId());}
        user.setEmail(userToSave.getEmail());
        user.setFirstName(userToSave.getFirstName());
        user.setLastName(userToSave.getLastName());
        user.setAccountName(userToSave.getAccountName());
        user.setAccountNumber(userToSave.getAccountNumber());
        user.setAccountBalance(userToSave.getAccountBalance());

        user.setPassword(userToSave.getPassword());
        user.setAddress(updateProfileRequest.getAddress());
        user.setPhone(updateProfileRequest.getPhone());
        user.setGender(userToSave.getGender());
        user.setStatus(userToSave.getStatus());
        user.setModifiedAt(LocalDateTime.now());
        userRepository.save(user);
        return modelMapper.map(user, UserDto.class);
    }

//    @Override
//    public User updateAllFields(long id, UpdateProfileRequest updateProfileRequest) throws TripuBankException {
//        return null;
//    }

    @Override
    public UserDto updateFewFields(long id, UpdateProfileRequest updateProfileRequest) throws TripuBankException {

        User user = userRepository.findById(id).orElseThrow(() ->
                new TripuBankException("User with id " + id + " does not exist", 401));
        if (updateProfileRequest.getFirstName() != null &&
                updateProfileRequest.getFirstName().length() > 0 &&
                !Objects.equals(user.getFirstName(), updateProfileRequest.getFirstName())){
            user.setFirstName(updateProfileRequest.getFirstName());
        }
        if (updateProfileRequest.getLastName() != null &&
            updateProfileRequest.getLastName().length() > 0 &&
            !Objects.equals(user.getLastName(), updateProfileRequest.getLastName())){
            user.setLastName(updateProfileRequest.getLastName());
        }
        if (updateProfileRequest.getEmail() != null &&
            updateProfileRequest.getEmail().length() > 0 &&
            !Objects.equals(user.getEmail(), updateProfileRequest.getEmail())){
            Optional <Boolean> userToCheck = Optional.ofNullable(userRepository.existsUserByEmail(updateProfileRequest.getEmail()));
            if (userToCheck.isPresent()){

            }
            user.setEmail(updateProfileRequest.getEmail());
        }
        if (updateProfileRequest.getPassword() != null &&
            updateProfileRequest.getPassword().length() > 0 &&
            !Objects.equals(user.getPassword(), updateProfileRequest.getPassword())){
            user.setEmail(updateProfileRequest.getPassword());
        }
        if (updateProfileRequest.getPhone() != null &&
            updateProfileRequest.getPhone().length() > 0 &&
            !Objects.equals(user.getPhone(), updateProfileRequest.getPhone())){
            user.setEmail(updateProfileRequest.getPhone());
        }
        return modelMapper.map(user, UserDto.class);
    }
}
