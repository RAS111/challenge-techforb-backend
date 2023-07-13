package com.challenge.techforb.enums;

public enum TypeTransactionName {
    MAKE_TRANSFER("MAKE_TRANSFER"), DEPOSIT("DEPOSIT"), WITHDRAW("WITHDRAW"),
    INCOME("INCOME"), PAYMENT("PAYMENT");

    private final String name;

    TypeTransactionName(String name) {
        this.name = name;
    }
    public TypeTransactionName getName() {
        return TypeTransactionName.valueOf(name);
    }
}