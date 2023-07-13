package com.challenge.techforb.repository;

import com.challenge.techforb.entity.Account;
import com.challenge.techforb.entity.Transaction;
import com.challenge.techforb.entity.User;
import com.challenge.techforb.enums.TypeTransactionName;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ITransactionRepository extends JpaRepository<Transaction, Long > {
    List<Transaction> findAllByAccountIdAndType(Account account, TypeTransactionName type);

    Long findByAccountId(User User);

    List<Transaction> findAllByUser(User user);
}
