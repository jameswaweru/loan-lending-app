package com.loanlender.app.dto.requests;

import lombok.Data;

@Data
public class ChangeCustomerLimit {
    private String msisdn;
    private double newLoanLimit;
}
