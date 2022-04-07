package com.loanlender.app.dto;

import lombok.Data;

@Data
public class LoanDetails {
    private int loanId;
    private double tenureRate;
    private double tenure;
    private double dailyPayment;
    private double totalPayment;
    private double loanAmount;
    private String description;
}
