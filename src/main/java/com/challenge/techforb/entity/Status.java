package com.challenge.techforb.entity;

import com.challenge.techforb.enums.StatusName;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@Entity
public class Status {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long statusId;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private StatusName name;

    @OneToMany(mappedBy = "status",fetch = FetchType.LAZY, cascade = CascadeType.MERGE)
    private List<Transaction> transactions = new ArrayList<>();
}
