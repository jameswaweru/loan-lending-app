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
}
