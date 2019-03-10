package com.dabanin.moneytransfer.repository;

import com.dabanin.moneytransfer.domain.Account;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class AccountRepository {

    private final Map<Long, Account> accountMap = new ConcurrentHashMap<>();

    public Account createAccount(Account account) {
        accountMap.put(account.getNumber(), account);
        return account;
    }

    public Account getAccount(Long accountNumber) {
        return accountMap.get(accountNumber);
    }

}
