package com.loanlender.app.repositories;

import com.loanlender.app.entity.CustomerLoanOffer;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CustomerLoanOfferRepository extends JpaRepository<CustomerLoanOffer , Integer> {
}
