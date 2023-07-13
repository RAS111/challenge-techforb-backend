package com.challenge.techforb.mapper;

import com.challenge.techforb.dto.TransactionDTO;
import com.challenge.techforb.entity.Transaction;
import com.challenge.techforb.entity.User;
import com.challenge.techforb.repository.IAccountRepository;
import com.challenge.techforb.repository.ITransactionRepository;
import com.challenge.techforb.repository.IUserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
public class TransactionMap {

    @Autowired
    private IAccountRepository iAccountRepository;
    @Autowired
    private ITransactionRepository iTransactionRepository;
    @Autowired
    private IUserRepository iUserRepository;

    public Transaction transactionDTO2Entity(TransactionDTO transactionDTO) {
        Transaction transaction = new Transaction();
        transaction.setTransactionId(transactionDTO.getTransactionId());
        transaction.setType(transactionDTO.getType());
        transaction.setDescription(transactionDTO.getDescription());
        transaction.setStatus(transactionDTO.getStatus());
        transaction.setAmount(transactionDTO.getAmount());
        transaction.setTransactionDate(transactionDTO.getTransactionDate());
        transaction.setAccountId(this.iAccountRepository.findByAccountId(transactionDTO.getAccountId()));

        return transaction;
    }

    public List<TransactionDTO> transactionEntityList2DTOList(List<Transaction> transactions) {
        List<TransactionDTO> transactionsDTO = new ArrayList<>();
        for(Transaction transaction : transactions){
            transactionsDTO.add(transactionEntity2DTO(transaction));
        }
        return transactionsDTO;

    }

    public TransactionDTO transactionEntity2DTO(Transaction transaction) {

        String email = SecurityContextHolder.getContext().getAuthentication().getName();
        User user = this.iUserRepository.findByEmail(email);
        TransactionDTO dto = new TransactionDTO();
        dto.setTransactionId(transaction.getTransactionId());
        dto.setAmount(transaction.getAmount());
        dto.setType(transaction.getType());
        dto.setTransactionDate(transaction.getTransactionDate());
        dto.setDescription(transaction.getDescription());
        dto.setStatus(transaction.getStatus());
        dto.setAccountId(transaction.getAccountId().getAccountId());
        dto.setDestinationAccountId(dto.getDestinationAccountId());

        return dto;
    }

}
