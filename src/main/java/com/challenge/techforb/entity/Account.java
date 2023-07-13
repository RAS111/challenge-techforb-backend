package com.challenge.techforb.entity;

import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.SQLDelete;
import org.hibernate.annotations.Where;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Getter
@Setter
@Entity
@SQLDelete(sql = "UPDATE account SET deleted = true WHERE account_id=?")
@Where(clause = "deleted=false")
public class Account {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long accountId;

    private double balance;

    private Date creationDate;

    private Date updateDate;

    @ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.MERGE)
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    @OneToMany(mappedBy = "accountId", cascade = CascadeType.MERGE)
    private List<Transaction> transactions = new ArrayList<>();

    private boolean deleted = Boolean.FALSE;
}
