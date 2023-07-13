package com.challenge.techforb.dto;

import com.challenge.techforb.enums.TypeTransactionName;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class DepositDTO {

    private Double amount;
    private String description;
    private TypeTransactionName type;

}

