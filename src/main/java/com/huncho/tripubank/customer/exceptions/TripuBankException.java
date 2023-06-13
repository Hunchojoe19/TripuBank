package com.huncho.tripubank.customer.exceptions;

import lombok.Getter;

@Getter
public class TripuBankException extends Exception{
    private int statusCode;

    public TripuBankException(String message, int statusCode) {
        super(message);
        this.statusCode = statusCode;
    }
}
