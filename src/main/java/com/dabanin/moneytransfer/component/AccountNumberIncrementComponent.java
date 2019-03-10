package com.dabanin.moneytransfer.component;

import java.util.concurrent.atomic.AtomicLong;

public class AccountNumberIncrementComponent {

    private AtomicLong currentAccountNumber = new AtomicLong(0L);

    public Long getCurrentAccountNumber() {
        return currentAccountNumber.getAndIncrement();
    }
}
