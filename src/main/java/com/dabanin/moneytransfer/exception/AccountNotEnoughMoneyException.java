package com.dabanin.moneytransfer.exception;

import io.vertx.ext.web.handler.impl.HttpStatusException;

public class AccountNotEnoughMoneyException extends HttpStatusException {
    public AccountNotEnoughMoneyException(Long accountNumber) {
        super(400, String.format("На счету с номером %d недостаточно средств для перевода", accountNumber));
    }
}
