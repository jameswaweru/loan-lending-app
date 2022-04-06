package com.loanlender.app.dto;

import lombok.Data;

@Data
public class LoanOffer {
    private int offerCode;
    private double tenureInterestRate;
    private int numberOfDays;
    private double loanAmount;

}
