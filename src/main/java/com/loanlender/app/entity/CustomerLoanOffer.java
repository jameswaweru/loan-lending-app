package com.loanlender.app.entity;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.loanlender.app.enums.LoanOfferStatus;
import lombok.Data;
import lombok.ToString;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import javax.persistence.*;
import java.util.Date;

@Data
@Entity
@ToString
@Table(name = "customerLoanOffers")
public class CustomerLoanOffer {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int loanOfferId;

    private double tenureInterestRate;
    private int numberOfDays;
    private double dailyPayment;
    private double totalPayment;
    private double loanAmount;

    private int customerId;

    private String offerCode;

    @Enumerated(value = EnumType.STRING)
    private LoanOfferStatus loanOfferStatus;

    @CreationTimestamp
    @JsonFormat(pattern="yyyy-MM-dd HH:mm:ss")
    private Date dateCreated;

    @UpdateTimestamp
    @JsonFormat(pattern="yyyy-MM-dd HH:mm:ss")
    private Date dateModified;

}
