package com.dabanin.moneytransfer.service;

import com.dabanin.moneytransfer.domain.Account;
import com.dabanin.moneytransfer.domain.Transfer;
import com.dabanin.moneytransfer.exception.AccountNotEnoughMoneyException;
import com.dabanin.moneytransfer.exception.SameAccountTransferException;
import com.dabanin.moneytransfer.exception.TransferAmountIncorrectValueException;
import com.google.inject.Inject;

import java.math.BigDecimal;

public class TransferService {

    private final AccountService accountService;

    @Inject
    public TransferService(AccountService accountService) {
        this.accountService = accountService;
    }

    public Transfer doTransfer(Transfer transfer) {
        BigDecimal transferAmount = transfer.getAmount();
        if (transferAmount.compareTo(BigDecimal.ZERO) <= 0) {
            throw new TransferAmountIncorrectValueException();
        }

        Long accountFromNumber = transfer.getFrom();
        Long accountToNumber = transfer.getTo();

        if (accountFromNumber.equals(accountToNumber)) {
            throw new SameAccountTransferException();
        }

        Account accountFrom = accountService.getAccount(accountFromNumber);
        Account accountTo = accountService.getAccount(accountToNumber);

        if (accountFromNumber < accountToNumber) {
            synchronized (accountFrom) {
                synchronized (accountTo) {
                    makeTransfer(transferAmount, accountFrom, accountTo);
                }
            }
        } else if (accountFromNumber > accountToNumber) {
            synchronized (accountTo) {
                synchronized (accountFrom) {
                    makeTransfer(transferAmount, accountFrom, accountTo);
                }
            }
        }
        return transfer;
    }

    private void makeTransfer(BigDecimal transferAmount, Account accountFrom, Account accountTo) {
        BigDecimal accountFromResultBalance = accountFrom.getBalance().subtract(transferAmount);
        if (accountFromResultBalance.compareTo(BigDecimal.ZERO) < 0) {
            throw new AccountNotEnoughMoneyException(accountFrom.getNumber());
        }

        accountFrom.setBalance(accountFromResultBalance);
        accountTo.setBalance(accountTo.getBalance().add(transferAmount));
    }

}
