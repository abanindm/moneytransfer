package com.dabanin.moneytransfer.domain;

import java.math.BigDecimal;

public class Account {

    private final Long number;
    private BigDecimal balance;

    public Account(Long number, BigDecimal balance) {
        this.number = number;
        this.balance = balance;
    }

    public Long getNumber() {
        return number;
    }

    public BigDecimal getBalance() {
        return balance;
    }

    public void setBalance(BigDecimal balance) {
        this.balance = balance;
    }
}
