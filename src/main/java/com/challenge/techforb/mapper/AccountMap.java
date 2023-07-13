package com.challenge.techforb.mapper;

import com.challenge.techforb.dto.AccountDTO;
import com.challenge.techforb.entity.Account;
import com.challenge.techforb.repository.ITransactionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
public class AccountMap {
    @Autowired
    private ITransactionRepository iTransactionRepository;
    @Autowired
    private TransactionMap transactionMap;

    private AccountDTO accountEntity2Dto(Account account) {
        AccountDTO dto = new AccountDTO();
        dto.setAccountId(account.getAccountId());
        dto.setUpdateDate(account.getUpdateDate());
        dto.setBalance(account.getBalance());
        dto.setCreationDate(account.getCreationDate());

        return dto;
    }

    public List<AccountDTO> accountEntityList2DTOList(List<Account> accounts) {
        List<AccountDTO> accountsDTO = new ArrayList<>();

        for (Account account : accounts){

            accountsDTO.add(accountEntity2Dto(account));
        }
        return accountsDTO;
    }


}
