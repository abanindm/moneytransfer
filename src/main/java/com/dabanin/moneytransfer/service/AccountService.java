package com.dabanin.moneytransfer.service;

import com.dabanin.moneytransfer.component.AccountNumberIncrementComponent;
import com.dabanin.moneytransfer.domain.Account;
import com.dabanin.moneytransfer.exception.AccountBalanceIncorrectValue;
import com.dabanin.moneytransfer.exception.AccountNotExistException;
import com.dabanin.moneytransfer.repository.AccountRepository;
import com.google.inject.Inject;

import java.math.BigDecimal;

public class AccountService {

    private final AccountNumberIncrementComponent incrementComponent;
    private final AccountRepository accountRepository;

    @Inject
    public AccountService(AccountNumberIncrementComponent incrementComponent, AccountRepository accountRepository) {
        this.incrementComponent = incrementComponent;
        this.accountRepository = accountRepository;
    }

    public Account crateAccount(BigDecimal balance) {
        if (balance.compareTo(BigDecimal.ZERO) < 0) {
            throw new AccountBalanceIncorrectValue();
        }
        Account newAccount = new Account(incrementComponent.getCurrentAccountNumber(), balance);
        return accountRepository.createAccount(newAccount);
    }

    public Account getAccount(Long number) {
        Account account = accountRepository.getAccount(number);
        if (account == null) {
            throw new AccountNotExistException(number);
        }
        return account;
    }
}
