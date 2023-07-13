package com.challenge.techforb.service.impl;

import com.challenge.techforb.auth.exception.ParamNotFound;
import com.challenge.techforb.dto.AccountDTO;
import com.challenge.techforb.entity.Account;
import com.challenge.techforb.entity.Transaction;
import com.challenge.techforb.entity.User;
import com.challenge.techforb.enums.TypeTransactionName;
import com.challenge.techforb.mapper.AccountMap;
import com.challenge.techforb.repository.IAccountRepository;
import com.challenge.techforb.repository.ITransactionRepository;
import com.challenge.techforb.repository.IUserRepository;
import com.challenge.techforb.service.IAccountService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

@Service
public class AccountServiceImpl implements IAccountService {

    @Autowired
    private IUserRepository userRepository;
    @Autowired
    private IAccountRepository iAccountRepository;
    @Autowired
    private ITransactionRepository iTransactionRepository;
    @Autowired
    private AccountMap accountMap;

    @Override
    public String addAccount(String email) {

        String userEmail = SecurityContextHolder.getContext().getAuthentication().getName();
        User user = this.userRepository.findByEmail(email);
        List<Account> accounts = this.iAccountRepository.findAllByUser(user);
        if (user == null) {
          throw new ParamNotFound("ID invalid");
        }

        Account account = createAccount(email);
        User findUser = this.userRepository.findByEmail(email);
        account.setUser(findUser);

        this.iAccountRepository.save(account);
        findUser.addAccount(account);
        this.userRepository.save(findUser);
        return HttpStatus.CREATED.getReasonPhrase();
    }

    @Override
    public Account createAccount(String email) {
        Account account = new Account();
        account.setCreationDate(new Date());
        account.setBalance(0.0);
        account.setUpdateDate(new Date());
        return account;
    }

    @Override
    public List<AccountDTO> getBalance() {
        String email = SecurityContextHolder.getContext().getAuthentication().getName();
        User user = userRepository.findByEmail(email);
        Long userId = user.getUserId();
        List<Account> accountList = iAccountRepository.findAllByUser(user);
        List<AccountDTO> accountDTO = accountMap.accountEntityList2DTOList(accountList);
        return accountDTO;
    }

    @Override
    public void updateBalance(Long accountId, Double amount, TypeTransactionName type) {
        Account account = iAccountRepository.findById(accountId).orElseThrow(
                ()-> new ParamNotFound("id invalid"));

        if (type == TypeTransactionName.PAYMENT
                || type == TypeTransactionName.WITHDRAW
                || type == TypeTransactionName.MAKE_TRANSFER){
            account.setBalance(account.getBalance() - amount);
        }
        if (type == TypeTransactionName.DEPOSIT || type == TypeTransactionName.INCOME){
            account.setBalance(account.getBalance() + amount);
        }

        iAccountRepository.save(account);
    }

    @Override
    public List<AccountDTO> finAllByUser(Long userId) {
        User entity = userRepository.findById(userId).orElseThrow(
                ()-> new ParamNotFound("User Id invalid"));
        List<Account> accounts = iAccountRepository.findAllByUser(entity);
        List<AccountDTO> accountDtoList = this.accountMap.accountEntityList2DTOList(accounts);

        return accountDtoList;
    }
}
