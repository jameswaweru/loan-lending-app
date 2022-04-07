package com.loanlender.app.implementation;

import com.loanlender.app.configs.LoanProperties;
import com.loanlender.app.dto.LoanDetails;
import com.loanlender.app.dto.LoanOffer;
import com.loanlender.app.dto.requests.LoanProductConversionConfigs;
import com.loanlender.app.entity.Customer;
import com.loanlender.app.interfaces.GetCustomerLoanOffers;
import com.loanlender.app.utils.LoanUtils;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class ProcessLoanOffers implements GetCustomerLoanOffers {

    private double tenureRate; //monthlyInterestRate;
    private double dailyInterestRate;
    private double dailyPayment;
    private double  tenure;   //numberOfMonths;
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
        tenureRate = loanDetails.getTenureRate(); //per month
        tenure = loanDetails.getTenure(); // days

        LoanProductConversionConfigs conversionRates = LoanUtils.getLoanProductConversionConfigs(loanAmount,tenure, tenureRate, loanProperties);

        //based on the initial amount stated by customer & the primary loan product conversion rates
        LoanOffer initialLoanOffer = new LoanOffer();
        //initialLoanOffer.setDailyPayment(conversionRates.getDailyPayment());
        initialLoanOffer.setLoanAmount(loanAmount);
        initialLoanOffer.setNumberOfDays(tenure);
        initialLoanOffer.setOfferCode(getRandomCode());
        initialLoanOffer.setTenureInterestRate(tenureRate);

        double loanOfferAmount = LoanUtils.calculateLoanAmountWithIncreasedTenureAndReducedDailyPayment(
                conversionRates.getDailyInterestRate(),
                conversionRates.getTenureTermInDays(),
                conversionRates.getDailyPayment(),
                loanProperties
                );

        LoanOffer firstLoanOffer = new LoanOffer();
        //firstLoanOffer.setDailyPayment(conversionRates.getDailyPayment());
        firstLoanOffer.setLoanAmount(loanOfferAmount);
        firstLoanOffer.setNumberOfDays(tenure+loanProperties.getTenureAdditionDays());
        firstLoanOffer.setOfferCode(getRandomCode());
        firstLoanOffer.setTenureInterestRate(tenureRate);
//        firstLoanOffer.setTotalPayment(getTotalPayment());

        loanOffers.add(firstLoanOffer);

        double secondLoanOfferAmount = LoanUtils.calculateLoanAmountWithReducedTenureAndIncreasedDailyPayment(
                conversionRates.getDailyInterestRate(),
                conversionRates.getTenureTermInDays(),
                conversionRates.getDailyPayment(),
                loanProperties
        );



        LoanOffer secondLoanOffer = new LoanOffer();
        //secondLoanOffer.setDailyPayment(conversionConfigs.getDailyPayment());
        secondLoanOffer.setLoanAmount(secondLoanOfferAmount);
        secondLoanOffer.setNumberOfDays(tenure-loanProperties.getTenureReductionDays());
        secondLoanOffer.setOfferCode(getRandomCode());
        secondLoanOffer.setTenureInterestRate(tenureRate);
//        secondLoanOffer.setTotalPayment(getTotalPayment());

        loanOffers.add(secondLoanOffer);

        return loanOffers;
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
