package com.huncho.tripubank.customer.RequestAndResponse;

import com.huncho.tripubank.customer.entity.enumeration.Gender;
import com.huncho.tripubank.customer.entity.enumeration.Role;
import com.huncho.tripubank.customer.entity.enumeration.Status;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.validator.constraints.Length;

import java.time.LocalDateTime;

@AllArgsConstructor
@NoArgsConstructor
@Builder
@Data
public class CreationRequest {
    private String firstName;
    @NotBlank
    @NotNull
    private String lastName;
    @NotNull
    @NotBlank
    private String address;
    private String email;
    @NotBlank
    @NotNull
    @Length(max = 12)
    private String phone;

    private String password;

    private Gender gender;

    private Status status;
    private Role role;

}
