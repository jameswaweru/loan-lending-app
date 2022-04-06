package com.loanlender.app.interfaces;

import com.loanlender.app.dto.LoanDetails;
import com.loanlender.app.dto.requests.LoanApplication;

public interface CalculateLoanDetails {
    public LoanDetails calculateLoanDetails(LoanDetails loanDetails);
}
