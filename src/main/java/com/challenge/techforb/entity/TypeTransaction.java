package com.challenge.techforb.entity;

import com.challenge.techforb.enums.TypeTransactionName;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@Entity
public class TypeTransaction {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long typeTransactionId;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private TypeTransactionName TypeName;

    @OneToMany(mappedBy = "type",fetch = FetchType.LAZY, cascade = CascadeType.MERGE)
    private List<Transaction> transactions = new ArrayList<>();

}
