package com.challenge.techforb.dto;

import lombok.*;

import java.util.Date;

@Getter
@Setter
public class AccountDTO {
    private Long accountId;
    private double balance;
    private Date creationDate;
    private Date updateDate;
    private Long userId;
}