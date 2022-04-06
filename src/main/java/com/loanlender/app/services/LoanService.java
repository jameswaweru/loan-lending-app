package com.loanlender.app.services;

import com.loanlender.app.entity.Customer;
import com.loanlender.app.entity.CustomerLoanOffer;
import com.loanlender.app.entity.LoanProduct;
import com.loanlender.app.repositories.CustomerLoanOfferRepository;
import com.loanlender.app.repositories.LoanProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class LoanService {


    @Autowired
    LoanProductRepository loanProductRepository;

    @Autowired
    CustomerLoanOfferRepository customerLoanOfferRepository;

    public CustomerLoanOffer findLoanOfferById(int loanOfferId){
        return customerLoanOfferRepository.findById(loanOfferId).orElse(new CustomerLoanOffer());
    }

    public List<CustomerLoanOffer> saveCustomerLoanOffers(List<CustomerLoanOffer> customerLoanOffers){
        return customerLoanOfferRepository.saveAll(customerLoanOffers);
    }

    public LoanProduct saveLoanProduct(LoanProduct loanProduct){
        return loanProductRepository.save(loanProduct);
    }

    public List<LoanProduct> saveLoanProducts(List<LoanProduct> loanProducts){
        return loanProductRepository.saveAll(loanProducts);
    }

    public List<LoanProduct> fetchAllLoanProducs(){
        return loanProductRepository.findAll();
    }
}
