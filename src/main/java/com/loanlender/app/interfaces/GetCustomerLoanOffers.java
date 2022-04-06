package com.loanlender.app.interfaces;

import com.loanlender.app.dto.LoanDetails;
import com.loanlender.app.dto.LoanOffer;
import com.loanlender.app.entity.Customer;

import java.util.List;

public interface GetCustomerLoanOffers {
    public List<LoanOffer> getCustomerLoanOffers(Customer customer, LoanDetails loanDetails);
}
