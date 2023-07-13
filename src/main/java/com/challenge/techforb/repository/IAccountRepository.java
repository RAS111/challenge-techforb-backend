package com.challenge.techforb.repository;

import com.challenge.techforb.entity.Account;
import com.challenge.techforb.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface IAccountRepository extends JpaRepository<Account, Long> {
    Account findByAccountId(Long accountId);
    Account findByUser(User user);

    List<Account> findAllByUser(User user);
}
