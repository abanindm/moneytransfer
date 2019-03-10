package com.dabanin.moneytransfer.controller;

import com.dabanin.moneytransfer.domain.Transfer;
import com.dabanin.moneytransfer.service.TransferService;
import com.google.inject.Inject;
import io.vertx.core.Handler;
import io.vertx.core.json.JsonObject;
import io.vertx.ext.web.RoutingContext;

import java.math.BigDecimal;

public class TransferController {

    private final TransferService transferService;

    @Inject
    public TransferController(TransferService transferService) {
        this.transferService = transferService;
    }

    public Handler<RoutingContext> doTransferHandler() {
        return routingContext -> {
            JsonObject requestBody = routingContext.getBodyAsJson();
            Long accountNumberFrom = requestBody.getLong("from");
            Long accountNumberTo = requestBody.getLong("to");
            BigDecimal amount = new BigDecimal(requestBody.getDouble("amount"));
            Transfer transfer = new Transfer(accountNumberFrom, accountNumberTo, amount, null);
            Transfer transferDto = transferService.doTransfer(transfer);
            JsonObject responseBody = new JsonObject();
            responseBody.put("to", transferDto.getTo());
            responseBody.put("from", transferDto.getFrom());
            responseBody.put("amount", transferDto.getAmount().doubleValue());
            routingContext.response().end(responseBody.encode());
        };
    }
}
