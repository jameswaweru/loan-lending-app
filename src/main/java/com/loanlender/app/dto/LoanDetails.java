package com.loanlender.app.dto;

import lombok.Data;

@Data
public class LoanDetails {
    private int loanId;
    private double tenureInterestRate;
    private int numberOfDays;
    private double dailyPayment;
    private double totalPayment;
    private double loanAmount;
}
