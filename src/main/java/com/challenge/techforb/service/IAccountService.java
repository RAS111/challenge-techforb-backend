package com.challenge.techforb.service;

import com.challenge.techforb.dto.AccountDTO;
import com.challenge.techforb.entity.Account;
import com.challenge.techforb.enums.TypeTransactionName;

import java.util.List;

public interface IAccountService {
    String addAccount(String email);

    Account createAccount(String email);

    List<AccountDTO> getBalance();


    void updateBalance(Long accountId, Double amount, TypeTransactionName type);

    List<AccountDTO> finAllByUser(Long userId);
}
