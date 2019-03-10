package com.dabanin.moneytransfer.exception;

import io.vertx.ext.web.handler.impl.HttpStatusException;

public class SameAccountTransferException extends HttpStatusException {
    public SameAccountTransferException() {
        super(400, "Невозможно совершить перевод на тот же счет");
    }
}
