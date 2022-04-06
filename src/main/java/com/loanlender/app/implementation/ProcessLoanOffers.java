package com.loanlender.app.implementation;

import com.loanlender.app.configs.LoanProperties;
import com.loanlender.app.dto.LoanDetails;
import com.loanlender.app.dto.LoanOffer;
import com.loanlender.app.entity.Customer;
import com.loanlender.app.interfaces.GetCustomerLoanOffers;

import java.util.List;

public class ProcessLoanOffers implements GetCustomerLoanOffers {

    private double currentDailyInterestRate;
    private double reducedDailyInterest;


    /**
     * @param customer
     *
     * for loan offers, if customer chooses less repayment days , interest should be lower
     * give two repayment days options for repayment
     *
     * i expect to receive loan amount & customer details (Max Limit)
     *
     * @return
     */

    @Override
    public List<LoanOffer> getCustomerLoanOffers(Customer customer , LoanDetails loanDetails,
                                                 LoanProperties loanProperties,
                                                 ProcessLoanDetails processLoanDetails
                                                 ) {

        currentDailyInterestRate = loanDetails.getTenureInterestRate() / (100); //calculated in percentage
        reducedDailyInterest = currentDailyInterestRate * loanProperties.getReducingRate();

        //get the loan amount customer wants


        //calculate the initial loan details
        LoanDetails defaultLoan = processLoanDetails.calculateLoanDetails(loanDetails);

        //reduce interest rate and recalculate the new daily repayment
        loanDetails.set
        LoanDetails loanOffer = processLoanDetails.calculateLoanDetails(loanDetails);


        return null;
    }

    private double reduceTheDailyInterestRate(double currentRate , double reducingRate){
        return currentRate * reducingRate ;
    }
}
