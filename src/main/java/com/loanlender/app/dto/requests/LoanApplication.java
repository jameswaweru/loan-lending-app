package com.loanlender.app.dto.requests;

import lombok.Data;

@Data
public class LoanApplication {
    private String customerMobile;
    private double loanAmount;
    private double tenureRate;
    private double tenure;
}
