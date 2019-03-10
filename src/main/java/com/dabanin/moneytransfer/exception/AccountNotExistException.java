package com.dabanin.moneytransfer.exception;

import io.vertx.ext.web.handler.impl.HttpStatusException;

public class AccountNotExistException extends HttpStatusException {
    public AccountNotExistException(Long accountNumber) {
        super(404, String.format("Аккаунт с номером %d не существует", accountNumber));
    }
}
