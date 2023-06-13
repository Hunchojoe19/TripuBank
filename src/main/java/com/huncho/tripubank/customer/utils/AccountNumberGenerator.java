package com.huncho.tripubank.customer.utils;

public class AccountNumberGenerator {

    public static final String RESPONSE_CODE = "400";
    public static final String RESPONSE_MESSAGE = "Email Already Exists";

    public static final String SUCCESS_CODE = "200";
    public static final String SUCCESS_MESSAGE = "Account created successfully!!";



    public static String accountNumberGenerator(){
        String firstTwoDigits = "102";
        int min = 1000000;
        int max = 9999999;

        int randomNumber = (int)Math.floor(Math.random() * (max - min + 1) + min);

        String secondPart = String.valueOf(randomNumber);

        StringBuilder accountNums = new StringBuilder();

        return (accountNums.append(firstTwoDigits).append(secondPart)).toString();
    }
}
