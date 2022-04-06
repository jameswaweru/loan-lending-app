package com.loanlender.app.dto;

import lombok.Data;

@Data
public class LoanOffer {
//    private double loanAmount;
//    private double interestRate;
//    private int tenure;

    private int loanId;
    private double tenureInterestRate;
    private int numberOfDays;
    private double dailyPayment;
    private double totalPayment;
    private double loanAmount;

}
