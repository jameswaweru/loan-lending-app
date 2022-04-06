package com.loanlender.app.repositories;

import com.loanlender.app.entity.LoanProduct;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface LoanProductRepository extends JpaRepository<LoanProduct , Integer> {
    @Override
    List<LoanProduct> findAll();
}
