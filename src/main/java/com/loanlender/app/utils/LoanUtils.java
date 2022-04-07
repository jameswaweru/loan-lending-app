package com.loanlender.app.utils;

import com.loanlender.app.configs.LoanProperties;
import com.loanlender.app.dto.requests.LoanProductConversionConfigs;

public class LoanUtils {

    /**
     *
     * @param loanAmount
     * @param termInMonths //months
     * @param interestRate //p.m
     * @param loanProperties
     * @return
     */
    public static LoanProductConversionConfigs getLoanProductConversionConfigs(
            double loanAmount, double termInMonths, double interestRate, LoanProperties loanProperties) {

        LoanProductConversionConfigs conversionConfigs = new LoanProductConversionConfigs();

        // Convert tenure rate into a decimal
        // eg. 2.5% = 0.025
        interestRate /= 100.0;

        // Daily interest rate
        // is the monthly rate divided by days in a month

        double dailyRate = interestRate / loanProperties.getDaysInAMonth();
        conversionConfigs.setDailyInterestRate(dailyRate);

        // The length of the term in days
        // is the number of months times 30

        double termInDays = termInMonths * 30;
        conversionConfigs.setTenureTermInDays(termInDays);

        // Calculate the monthly payment
        // The Math.pow() calculates power of a number

//        double dailyPayment =
//                (loanAmount*dailyRate) /
//                        (1-Math.pow(1+dailyRate, -termInDays));
        double dailyPayment = loanAmount * dailyRate / (1 -
                (1 / Math.pow(1 + dailyRate, termInDays)));
        conversionConfigs.setDailyPayment(dailyPayment);

        return conversionConfigs;
    }

    public static double calculateLoanAmountWithIncreasedTenureAndReducedDailyPayment(double dailyRate , double termInDays, double dailyPayment, LoanProperties loanProperties){
        termInDays = termInDays + loanProperties.getTenureAdditionDays();
        dailyPayment = dailyPayment * loanProperties.getReducingRate();
        double loanAmount = (dailyPayment * (1-Math.pow(1+dailyRate, -termInDays)) ) / dailyRate;
        return round(loanAmount , 2);
    }



    public static double calculateLoanAmountWithReducedTenureAndIncreasedDailyPayment(double dailyRate , double termInDays, double dailyPayment, LoanProperties loanProperties){
        termInDays = termInDays - loanProperties.getTenureReductionDays();
        dailyPayment = dailyPayment * loanProperties.getIncrementRate();
        double loanAmount = (dailyPayment * (1-Math.pow(1+dailyRate, -termInDays)) ) / dailyRate;
        return round(loanAmount , 2);
    }

    /**
     * Rounds off double to the nearest precision number provided
     * @param value
     * @param precision
     * @return
     */
    public static double round (double value, int precision) {
        int scale = (int) Math.pow(10, precision);
        return (double) Math.round(value * scale) / scale;
    }


}
