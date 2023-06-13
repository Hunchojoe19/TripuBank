package com.huncho.tripubank.customer.RequestAndResponse;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Builder
@AllArgsConstructor
@NoArgsConstructor
@Data
public class AuthenticationResponse {
   private String token;
    private Object userDetails;
    private int statusCode;
}
