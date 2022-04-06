package com.loanlender.app.services;

import com.loanlender.app.entity.Customer;
import com.loanlender.app.repositories.CustomerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class LoanCustomerService {

    @Autowired
    CustomerRepository customerRepository;



    public Customer saveCustomer(Customer customer){
        return customerRepository.save(customer);
    }

    public Customer findCustomerByMsisdn(String msisdn){
        List<Customer> results = customerRepository.findByMsisdn(msisdn);
        if(results.size()>0){
            return results.get(0);
        }
        return new Customer();
    }


}
