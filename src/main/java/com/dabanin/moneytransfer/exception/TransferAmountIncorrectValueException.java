package com.dabanin.moneytransfer.exception;

import io.vertx.ext.web.handler.impl.HttpStatusException;

public class TransferAmountIncorrectValueException extends HttpStatusException {
    public TransferAmountIncorrectValueException() {
        super(400, "Сумма перевода меньше или равна нулю");
    }
}
