package com.loanlender.app.implementation;

import com.loanlender.app.dto.LoanDetails;
import com.loanlender.app.dto.requests.LoanApplication;
import com.loanlender.app.interfaces.CalculateLoanDetails;

public class ProcessLoanDetails implements CalculateLoanDetails {

    private double dailyInterest;
    private double dailyPayment;
    private double totalPayment;

    /**
     *
     * @param loanDetails
     *
     * i expect to receive loanAmount, tenureInterestRate
     *  and tenure(days)
     * tenure given in days
     *
     *  calculate dailyPayment , totalPayment
     *
     * @return
     */

    @Override
    public LoanDetails calculateLoanDetails(LoanDetails loanDetails) {

        LoanDetails calculatedLoanDetails = new LoanDetails();

        dailyInterest = loanDetails.getTenureInterestRate() / (100); //calculated in percentage
        dailyPayment = loanDetails.getLoanAmount() * dailyInterest / (1 -
                (1 / Math.pow(1 + dailyInterest, loanDetails.getNumberOfDays())));
        totalPayment = dailyPayment * loanDetails.getNumberOfDays();

        calculatedLoanDetails.setLoanId(1);
        calculatedLoanDetails.setLoanAmount(loanDetails.getLoanAmount());
        calculatedLoanDetails.setDailyPayment(dailyPayment);
        calculatedLoanDetails.setTotalPayment(totalPayment);
        calculatedLoanDetails.setTenureInterestRate(loanDetails.getTenureInterestRate());
        calculatedLoanDetails.setNumberOfDays(loanDetails.getNumberOfDays());

        return calculatedLoanDetails;
    }

}
