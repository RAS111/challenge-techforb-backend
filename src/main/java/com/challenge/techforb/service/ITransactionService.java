package com.challenge.techforb.service;

import com.challenge.techforb.dto.DepositDTO;
import com.challenge.techforb.dto.PaymentDTO;
import com.challenge.techforb.dto.TransactionDTO;

import java.util.List;

public interface ITransactionService {

    TransactionDTO sendPayment(PaymentDTO paymentDTO);
    TransactionDTO sendDeposit(DepositDTO depositDTO);
    TransactionDTO createTransaction(TransactionDTO transactionDto);
    List<TransactionDTO> getTransactions();
}
