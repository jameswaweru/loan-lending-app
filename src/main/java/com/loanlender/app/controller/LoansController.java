package com.loanlender.app.controller;

import com.loanlender.app.configs.LoanProperties;
import com.loanlender.app.dto.LoanDetails;
import com.loanlender.app.dto.LoanOffer;
import com.loanlender.app.dto.MobileWallet;
import com.loanlender.app.dto.requests.ChangeCustomerLimit;
import com.loanlender.app.dto.requests.LoanApplication;
import com.loanlender.app.entity.Customer;
import com.loanlender.app.entity.LoanProduct;
import com.loanlender.app.exceptions.RequestException;
import com.loanlender.app.implementation.ProcessLoanOffers;
import com.loanlender.app.services.LoanCustomerService;
import com.loanlender.app.services.LoanService;
import com.loanlender.app.utils.Utilities;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Slf4j
@RestController
@RequestMapping("api/loans")
public class LoansController {

    @Autowired
    ProcessLoanOffers processLoanOffers;
    @Autowired
    LoanProperties loanProperties;
    @Autowired
    LoanCustomerService loanCustomerService;
    @Autowired
    LoanService loanService;

    @PostMapping("saveLoanProducts")
    public ResponseEntity<Object> saveLoanProducts(@RequestBody List<LoanProduct> loanProducts) throws RequestException {
        Map<String, Object> response = new HashMap<>();
        List<LoanProduct> savedLoanProducts = loanService.saveLoanProducts(loanProducts);
        if(savedLoanProducts.size()>0){
            response.put("statusCode" , HttpStatus.OK.value());
            response.put("statusDescription", "Loan products saved");
            response.put("data", savedLoanProducts);
            return new ResponseEntity<>(response, HttpStatus.OK);
        }
        response.put("statusCode" , HttpStatus.NOT_IMPLEMENTED.value());
        response.put("statusDescription", "Loan products not saved");
        response.put("data", "");
        return new ResponseEntity<>(response, HttpStatus.NOT_IMPLEMENTED);
    }

    @PostMapping("changeCustomerLimit")
    public ResponseEntity<Object> changeCustomerLimit(@RequestBody ChangeCustomerLimit changeCustomerLimit) throws RequestException {
        Map<String, Object> response = new HashMap<>();

        changeCustomerLimit.setMsisdn(Utilities.sanitizeMobileNumber(changeCustomerLimit.getMsisdn()));

        Customer customer = loanCustomerService.findCustomerByMsisdn(changeCustomerLimit.getMsisdn());
        if(customer.getCustomerId()!=0){
            customer.setLoanLimit(changeCustomerLimit.getNewLoanLimit());
            loanCustomerService.saveCustomer(customer);

            response.put("statusCode" , HttpStatus.OK.value());
            response.put("statusDescription", "Successfully updated");
            response.put("data", "");
            return new ResponseEntity<>(response, HttpStatus.OK);
        }
        response.put("statusCode" , HttpStatus.NOT_FOUND.value());
        response.put("statusDescription", "No customer found with mobile number "+changeCustomerLimit.getMsisdn());
        response.put("data", "");
        return new ResponseEntity<>(response, HttpStatus.NOT_FOUND);
    }

    @PostMapping("apply")
    public ResponseEntity<Object> applyLoan(@RequestBody LoanApplication loanApplication) throws RequestException {
        Map<String, Object> response = new HashMap<>();

        log.info("Apply loan request{}",loanApplication);

        //sanitize mobile number
        loanApplication.setCustomerMobile(Utilities.sanitizeMobileNumber(loanApplication.getCustomerMobile()));

        //check customer details
        Customer customerDetails = loanCustomerService.findCustomerByMsisdn(loanApplication.getCustomerMobile());
        if(customerDetails.getCustomerId() == 0){
            customerDetails = loanCustomerService.saveCustomer(Utilities.generateCustomerInitialDetails(loanApplication,loanProperties));
        }

        //check loan product customer is seeking
        LoanProduct loanProduct = new LoanProduct();
        List<LoanProduct> loanProducts = loanService.fetchAllLoanProducs();

        for(LoanProduct loan : loanProducts){
            if(loanApplication.getLoanAmount() >= loan.getLoanProductMinLimit()
                    && loanApplication.getLoanAmount() <= loan.getLoanProductMaxLimit()){
                loanProduct = loan;
            }
        }

        //incase loan product isnt found, terminate the process
        if(loanProduct.getLoanProductId() == 0){
            response.put("statusCode" , HttpStatus.NOT_FOUND.value());
            response.put("statusDescription", "Sorry, we can't process your loan request now. Try again later, thank you." );
            response.put("data", "");
            return new ResponseEntity<>(response, HttpStatus.NOT_FOUND);
        }

        //check customer limit
        if(loanApplication.getLoanAmount()> customerDetails.getLoanLimit()){
            response.put("statusCode" , HttpStatus.NOT_IMPLEMENTED.value());
            response.put("statusDescription", "Sorry, your maximum loan limit is "+customerDetails.getLoanLimit() );
            response.put("data", "");
            return new ResponseEntity<>(response, HttpStatus.NOT_IMPLEMENTED);
        }

        //set the loan tenure & rate , for the selected loan product here
        loanApplication.setTenure(loanProduct.getTenure());
        loanApplication.setTenureRate(loanProduct.getTenureRate());

        LoanDetails customerInitialLoanDetails = Utilities.processInitialCustomerLoanDetails(loanApplication);

        //calculate loan offers
        List<LoanOffer> loanOffers = processLoanOffers.getCustomerLoanOffers(customerDetails,customerInitialLoanDetails,loanProperties);


        response.put("statusCode" , HttpStatus.OK.value());
        response.put("statusDescription", "Reply with offer code to select the loan product ");
        response.put("data", loanOffers);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @PostMapping("acceptLoanOffer")
    public ResponseEntity<String> acceptLoanOffer(@RequestBody LoanOffer loanOffer) throws RequestException {
        String creditWalletResponse = new MobileWallet(loanOffer.getLoanAmount()).checkStatus();
        if(creditWalletResponse.equals("failed")){
            return ResponseEntity.status(HttpStatus.NOT_IMPLEMENTED).body("Failed");
        }
        return ResponseEntity.status(HttpStatus.OK).body("Success");
    }






}
