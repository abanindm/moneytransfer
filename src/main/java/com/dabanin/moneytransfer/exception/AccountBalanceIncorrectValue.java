package com.dabanin.moneytransfer.exception;

import io.vertx.ext.web.handler.impl.HttpStatusException;

public class AccountBalanceIncorrectValue extends HttpStatusException {

    public AccountBalanceIncorrectValue() {
        super(400, "Баланс счета не может быть отрицательным");
    }
}
