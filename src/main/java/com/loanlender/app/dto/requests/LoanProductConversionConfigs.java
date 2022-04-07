package com.loanlender.app.dto.requests;

import lombok.Data;

@Data
public class LoanProductConversionConfigs {
    double dailyInterestRate;
    double tenureTermInDays;
    double dailyPayment;
}
