package com.challenge.techforb.service.impl;

import com.challenge.techforb.auth.exception.ParamNotFound;
import com.challenge.techforb.dto.DepositDTO;
import com.challenge.techforb.dto.PaymentDTO;
import com.challenge.techforb.dto.TransactionDTO;
import com.challenge.techforb.entity.Account;
import com.challenge.techforb.entity.Transaction;
import com.challenge.techforb.entity.User;
import com.challenge.techforb.enums.StatusName;
import com.challenge.techforb.enums.TypeTransactionName;
import com.challenge.techforb.mapper.TransactionMap;
import com.challenge.techforb.repository.IAccountRepository;
import com.challenge.techforb.repository.ITransactionRepository;
import com.challenge.techforb.repository.IUserRepository;
import com.challenge.techforb.service.IAccountService;
import com.challenge.techforb.service.ITransactionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;
import java.util.Objects;

@Service
public class TransactionServiceImpl implements ITransactionService {

    @Autowired
    private IUserRepository userRepository;
    @Autowired
    private IAccountRepository iAccountRepository;
    @Autowired
    private TransactionMap transactionMap;
    @Autowired
    private IAccountService iAccountService;
    @Autowired
    private ITransactionRepository iTransactionRepository;

    @Override
    public TransactionDTO sendPayment(PaymentDTO paymentDTO) {
        String email = SecurityContextHolder.getContext().getAuthentication().getName();
        User user = userRepository.findByEmail(email);
        Account account = iAccountRepository.findByUser(user);
        if(account.getBalance() < paymentDTO.getAmount()){
            throw new ParamNotFound("you need more money in your account to perform this operation");
        }
        Account receive = iAccountRepository.findById(
                paymentDTO.getDestinationAccountId()).orElseThrow(
                ()-> new ParamNotFound("The destination account do not exist"));
        if (user == null || receive == null || paymentDTO.getType() == null){
            throw new ParamNotFound("Invalid Operation");
        }
        if(paymentDTO.getAmount() <= 0){
            throw new ParamNotFound("Ammount must be greater than 0 (zero)");
        }
        if(paymentDTO.getType() == TypeTransactionName.INCOME
                || paymentDTO.getType() == TypeTransactionName.DEPOSIT){
            throw new ParamNotFound("Cannot make deposits");
        }

        TransactionDTO send = new TransactionDTO();
        send.setAmount(paymentDTO.getAmount());
        send.setDescription(paymentDTO.getDescription());
        send.setAccountId(account.getAccountId());
        send.setDestinationAccountId(receive.getAccountId());
        send.setType(paymentDTO.getType());
        send.setStatus(StatusName.COMPLETE);
        send.setTransactionDate(new Date());
        TransactionDTO transactionDTO = createTransaction(send);

        TransactionDTO receiver = new TransactionDTO();
        receiver.setAmount(paymentDTO.getAmount());
        receiver.setDescription(paymentDTO.getDescription());
        receiver.setAccountId(receive.getAccountId());
        receiver.setType(TypeTransactionName.INCOME);
        receiver.setStatus(StatusName.COMPLETE);
        receiver.setTransactionDate(new Date());
        createTransaction(receiver);

        return transactionDTO;
    }

    @Override
    public TransactionDTO sendDeposit(DepositDTO depositDTO) {
        String email = SecurityContextHolder.getContext().getAuthentication().getName();
        User user = userRepository.findByEmail(email);
        Account account = iAccountRepository.findByUser(user);

        if (user == null){
            throw new ParamNotFound("Invalid Operation");
        }
        if(depositDTO.getAmount() <= 0){
            throw new ParamNotFound("Ammount must be greater than 0 (zero)");
        }
        if(depositDTO.getType() == TypeTransactionName.PAYMENT
                || depositDTO.getType() == TypeTransactionName.WITHDRAW
                || depositDTO.getType() == TypeTransactionName.MAKE_TRANSFER){
            throw new ParamNotFound("Payments and transfers cannot be made");
        }

        TransactionDTO send = new TransactionDTO();
        send.setAmount(depositDTO.getAmount());
        send.setDescription(depositDTO.getDescription());
        send.setAccountId(account.getAccountId());
        send.setType(TypeTransactionName.DEPOSIT);
        send.setStatus(StatusName.COMPLETE);
        send.setTransactionDate(new Date());
        TransactionDTO transactionDTO = createTransaction(send);

        return transactionDTO;
    }

    @Override
    public TransactionDTO createTransaction(TransactionDTO transactionDTO) {
        if(transactionDTO.getAmount() <= 0){
            throw new ParamNotFound("The amount most be greater than 0 (zero)");
        } else {
            Transaction transaction = transactionMap.transactionDTO2Entity(transactionDTO);
            Account account = iAccountRepository.findByAccountId(transactionDTO.getAccountId());
            transaction.setAmount(transactionDTO.getAmount());
            transaction.setType(transactionDTO.getType());
            transaction.setAccountId(account);
            transaction.setUser(account.getUser());
            transaction.setDescription(transactionDTO.getDescription());
            transaction.setTransactionDate(new Date());
            this.iAccountService.updateBalance(
                    transactionDTO.getAccountId(),transactionDTO.getAmount(),transactionDTO.getType());
            this.iTransactionRepository.save(transaction);
            transactionDTO.setTransactionId(transaction.getTransactionId());
            return transactionDTO;
        }
    }

    @Override
    public List<TransactionDTO> getTransactions() {
        String email = SecurityContextHolder.getContext().getAuthentication().getName();
        User user = userRepository.findByEmail(email);
        Long userId = user.getUserId();
        List<Transaction> transactionsList = this.iTransactionRepository.findAllByUser(user);
        List<TransactionDTO> transactions = this.transactionMap.transactionEntityList2DTOList(transactionsList);

        return transactions;
    }
}
