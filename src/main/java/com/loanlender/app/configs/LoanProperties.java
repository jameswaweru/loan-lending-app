package com.loanlender.app.configs;

import lombok.Getter;
import lombok.Setter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;

@Configuration
@Getter
@Setter
public class LoanProperties {
    @Value("${daily.interest.rate.reduction.fraction}")
    public double reducingRate;

    @Value(("${daily.interest.rate.increment.fraction}"))
    public double incrementRate;

    @Value("${loan.offer.tenure.reduction.days}")
    public int tenureReductionDays;

    @Value("${loan.offer.tenure.addition.days}")
    public int tenureAdditionDays;

    @Value("${new.customer.loan.max.allowable.limit}")
    public int newCustomerMaxAllowableLoanLimit;

    @Value("${days.in.a.year}")
    public double daysInAYear;

    @Value(("${days.in.a.month}"))
    public double daysInAMonth;

}
