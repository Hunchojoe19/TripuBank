package com.huncho.tripubank.customer.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
public class Account {
    private String accountName;
    private String accountNumber;
    private BigDecimal accountBalance;
}
