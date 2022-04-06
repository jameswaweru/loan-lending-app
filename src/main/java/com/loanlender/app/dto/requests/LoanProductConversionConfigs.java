package com.loanlender.app.dto.requests;

import lombok.Data;

@Data
public class LoanProductConversionConfigs {
    double dailyInterestRate;
    int tenureTermInDays;
    double dailyPayment;
}
