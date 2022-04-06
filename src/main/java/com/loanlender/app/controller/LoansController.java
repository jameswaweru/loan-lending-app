package com.loanlender.app.controller;

import com.loanlender.app.dto.Loan;
import com.loanlender.app.dto.requests.LoanApplication;
import com.loanlender.app.exceptions.RequestException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

@Slf4j
@RestController
@RequestMapping("api/loans")
public class LoansController {

    @PostMapping("test")
    public ResponseEntity<Object> test(@RequestBody LoanApplication loanApplication) throws RequestException {
        Map<String, Object> response = new HashMap<>();

        log.debug("test loan calc : {}",loanApplication);

        Loan loan = new Loan(10,1,1000);

        response.put("loan",loan);


        return new ResponseEntity<>(response , HttpStatus.OK);
    }




}
