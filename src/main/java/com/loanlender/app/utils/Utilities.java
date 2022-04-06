package com.loanlender.app.utils;

import com.loanlender.app.configs.AppConstants;
import com.loanlender.app.configs.LoanProperties;
import com.loanlender.app.dto.LoanDetails;
import com.loanlender.app.dto.requests.LoanApplication;
import com.loanlender.app.entity.Customer;

import java.util.Map;

public class Utilities {
//    public static String formulateMessage(String templateString , Map<String, String> valuesMap){
//        // Initialize StringSubstitutor instance with value map
//        StringSubstitutor stringSubstitutor = new StringSubstitutor(valuesMap);
//
//        // replace value map to template string
//        String result = stringSubstitutor.replace(templateString);
//
//        return result;
//    }


    public static Customer generateCustomerInitialDetails(LoanApplication loanApplication , LoanProperties loanProperties){
       Customer customer = new Customer();
       customer.setLoanLimit(loanProperties.getNewCustomerMaxAllowableLoanLimit());
       customer.setCustomerName("");
       customer.setMsisdn(loanApplication.getCustomerMobile());
       return customer;
    }

    public static LoanDetails processInitialCustomerLoanDetails(LoanApplication loanApplication){
        LoanDetails loanDetails = new LoanDetails();
        loanDetails.setDailyPayment(0);
        loanDetails.setLoanAmount(loanApplication.getLoanAmount());
        loanDetails.setTenure(loanApplication.getTenure());
        loanDetails.setTenureRate(loanApplication.getTenureRate());
        loanDetails.setTotalPayment(0);
        return loanDetails;
    }

    /**
     * To check if a value is empty or null
     * @param value Object being determined
     * @return boolean true|false
     */
    public static boolean isEmpty(Object value) {
        boolean isEmpty = false;
        if (value == null || value.toString().replaceAll("\\s", String.valueOf(value)).equals("")) {
            isEmpty = true;
        }
        return isEmpty;
    }

    public static String sanitizeMobileNumber(String mobileNumber){
        String sanitizedNumber = mobileNumber;
        if(mobileNumber.length() == 9){
            sanitizedNumber = AppConstants.COUNTRY_DIAL_CODE+""+mobileNumber;
            return sanitizedNumber;
        }

        if(mobileNumber.length() == 10
                && String.valueOf(mobileNumber.charAt(0)).equals("0")){
            sanitizedNumber = AppConstants.COUNTRY_DIAL_CODE+""+mobileNumber.substring(Math.max(mobileNumber.length() - 9, 0));
        }



        return sanitizedNumber;
    }

}
