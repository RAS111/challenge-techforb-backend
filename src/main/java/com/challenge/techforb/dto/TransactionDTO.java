package com.challenge.techforb.dto;

import com.challenge.techforb.enums.StatusName;
import com.challenge.techforb.enums.TypeTransactionName;
import lombok.Getter;
import lombok.Setter;

import java.util.Date;

@Getter
@Setter
public class TransactionDTO {
    private Long transactionId;
    private Double amount;
    private TypeTransactionName type;
    private String description;
    private StatusName status;
    private Long accountId;
    private Long destinationAccountId;
    private Date transactionDate;
}
