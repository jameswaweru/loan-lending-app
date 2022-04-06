package com.loanlender.app.implementation;

import com.loanlender.app.configs.LoanProperties;
import com.loanlender.app.dto.LoanDetails;
import com.loanlender.app.dto.LoanOffer;
import com.loanlender.app.entity.Customer;
import com.loanlender.app.interfaces.GetCustomerLoanOffers;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class ProcessLoanOffers implements GetCustomerLoanOffers {

    private double tenureRate; //annualInterestRate;
    private double dailyInterestRate;
    private double dailyPayment;
    private int  tenure;   //numberOfYears;
    private double loanAmount;



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
    public List<LoanOffer> getCustomerLoanOffers(Customer customer ,
                                                 LoanDetails loanDetails,
                                                 LoanProperties loanProperties
                                                 ) {

        List<LoanOffer> loanOffers = new ArrayList<>();
        loanAmount = loanDetails.getLoanAmount();
//        tenureRate = loanDetails.getTenureRate();
//        tenure = loanDetails.getTenure();

        //reduce rate by 3/4 , reduce repayment days by given value and calculate new loan Details, do this for

        tenureRate = loanDetails.getTenureRate() * loanProperties.getReducingRate();
        tenure = loanDetails.getTenure() - loanProperties.getTenureReductionDays();

        LoanOffer firstLoanOffer = new LoanOffer();
        firstLoanOffer.setDailyPayment(getDailyPayment());
        firstLoanOffer.setLoanAmount(loanDetails.getLoanAmount());
        firstLoanOffer.setNumberOfDays(tenure);
        firstLoanOffer.setOfferCode(getRandomCode());
        firstLoanOffer.setTenureInterestRate(tenureRate);
        firstLoanOffer.setTotalPayment(getTotalPayment());

        loanOffers.add(firstLoanOffer);

        //lower tenure & tenure rate
        tenureRate = tenureRate * loanProperties.getReducingRate();
        tenure = tenure - loanProperties.getTenureReductionDays();


        LoanOffer secondLoanOffer = new LoanOffer();
        secondLoanOffer.setDailyPayment(getDailyPayment());
        secondLoanOffer.setLoanAmount(loanDetails.getLoanAmount());
        secondLoanOffer.setNumberOfDays(tenure);
        secondLoanOffer.setOfferCode(getRandomCode());
        secondLoanOffer.setTenureInterestRate(tenureRate);
        secondLoanOffer.setTotalPayment(getTotalPayment());

        loanOffers.add(secondLoanOffer);

        return loanOffers;
    }


    /** Find daily payment */
    private double getDailyPayment() {
         double dailyInterestRate = tenureRate / (tenure * 100);
         double dailyPayment = loanAmount * dailyInterestRate / (1 -
                (1 / Math.pow(1 + dailyInterestRate, tenure)));
        return dailyPayment;
    }

    /** Find total payment */
    private double getTotalPayment() {
        double totalPayment = getDailyPayment()* tenure;
        return totalPayment;
    }

    private int getRandomCode(){
        int min = 1000;
        int max = 9999;
        return (int)(Math.random()*(max-min+1)+min);
    }

    private double reduceTheDailyInterestRate(double currentRate , double reducingRate){
        return currentRate * reducingRate ;
    }

    private int reduceTenure(int currentTenure , int reducingRate){
        return currentTenure - reducingRate;
    }
}
