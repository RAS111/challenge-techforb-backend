package com.challenge.techforb.repository;

import com.challenge.techforb.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface IUserRepository extends JpaRepository<User, Long> {

    User findByEmail(String email);


    User findByUserId(Long userId);
}
