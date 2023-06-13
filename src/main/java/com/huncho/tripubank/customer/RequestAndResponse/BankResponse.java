package com.huncho.tripubank.customer.RequestAndResponse;

import com.huncho.tripubank.customer.dtos.UserDto;
import com.huncho.tripubank.customer.entity.Account;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Builder
@AllArgsConstructor
@NoArgsConstructor
@Data
public class BankResponse {
    private String responseCode;
    private String responseMessage;
    private Account accountInfo;
    private Object data;
}
