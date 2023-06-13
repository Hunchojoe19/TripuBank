package com.huncho.tripubank.customer.dtos;

import com.huncho.tripubank.customer.RequestAndResponse.BankResponse;
import com.huncho.tripubank.customer.entity.Account;
import com.huncho.tripubank.customer.entity.enumeration.Gender;
import com.huncho.tripubank.customer.entity.enumeration.Status;
import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;
import org.hibernate.validator.constraints.Length;
import org.springframework.hateoas.RepresentationModel;

import java.io.Serializable;
import java.time.LocalDateTime;
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Getter
@Setter
public class UserDto extends RepresentationModel<UserDto> implements Serializable {

    private Long id;

    private String firstName;

    private String lastName;

    private String address;

    private String email;

    private String password;

    private String phone;

    private Gender gender;

    private Status status;

    private transient Account account;

    @CreationTimestamp
    private LocalDateTime createdAt;
    @UpdateTimestamp
    private LocalDateTime modifiedAt;
}
