package com.dabanin.moneytransfer.domain;

import java.math.BigDecimal;

public class Transfer {

    private final Long from;
    private final Long to;
    private final BigDecimal amount;

    public Transfer(Long from, Long to, BigDecimal amount, String uid) {
        this.from = from;
        this.to = to;
        this.amount = amount;
    }

    public Long getFrom() {
        return from;
    }

    public Long getTo() {
        return to;
    }

    public BigDecimal getAmount() {
        return amount;
    }

}
