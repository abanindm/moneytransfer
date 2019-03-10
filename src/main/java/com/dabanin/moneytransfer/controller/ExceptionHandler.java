package com.dabanin.moneytransfer.controller;

import io.netty.handler.codec.http.HttpResponseStatus;
import io.vertx.core.Handler;
import io.vertx.core.json.JsonObject;
import io.vertx.ext.web.RoutingContext;
import io.vertx.ext.web.handler.impl.HttpStatusException;

public class ExceptionHandler {

    public Handler<RoutingContext> getExceptionHandler() {
        return routingContext -> {
            Throwable failure = routingContext.failure();

            final JsonObject errorResponse = new JsonObject();
            errorResponse.put("timestamp", System.nanoTime())
                    .put("exception", failure.getClass().getName());

            if (failure instanceof HttpStatusException) {
                HttpStatusException exception = (HttpStatusException) failure;
                errorResponse.put("status", exception.getStatusCode())
                        .put("error", HttpResponseStatus.valueOf(exception.getStatusCode()).reasonPhrase())
                        .put("message", exception.getPayload());
            } else if (failure instanceof NumberFormatException) {
                errorResponse.put("status", 400)
                        .put("error", HttpResponseStatus.valueOf(400).reasonPhrase())
                        .put("message", "Введенное числовое значение не верно");
            } else {
                RuntimeException exception = (RuntimeException) failure;
                errorResponse.put("status", 500)
                        .put("error", HttpResponseStatus.valueOf(500).reasonPhrase())
                        .put("message", exception.getLocalizedMessage());
            }
            routingContext.response().setStatusCode(errorResponse.getInteger("status"));
            routingContext.response().end(errorResponse.encode());
        };
    }
}
