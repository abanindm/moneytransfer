package com.dabanin.moneytransfer.exception;

import io.vertx.ext.web.handler.impl.HttpStatusException;

public class InvalidRequestException extends HttpStatusException {
    public InvalidRequestException(String payload) {
        super(400, payload);
    }
}
