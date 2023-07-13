package com.challenge.techforb.enums;

public enum StatusName {
    CANCELLED("CANCELLED"), PENDING("PENDING"), COMPLETE("COMPLETE");

    private final String name;

    StatusName(String name) {
        this.name = name;
    }
    public StatusName getName() {
        return StatusName.valueOf(name);
    }
}
