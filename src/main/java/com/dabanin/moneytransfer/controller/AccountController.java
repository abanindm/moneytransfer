package com.dabanin.moneytransfer.controller;

import com.dabanin.moneytransfer.domain.Account;
import com.dabanin.moneytransfer.exception.InvalidRequestException;
import com.dabanin.moneytransfer.service.AccountService;
import com.google.inject.Inject;
import io.vertx.core.Handler;
import io.vertx.core.json.JsonObject;
import io.vertx.ext.web.RoutingContext;

import java.math.BigDecimal;

public class AccountController {

    private final AccountService accountService;

    @Inject
    public AccountController(AccountService accountService) {
        this.accountService = accountService;
    }

    public Handler<RoutingContext> createAccountHandler() {
        return routingContext -> {
            String balanceFromJson = routingContext.getBodyAsString();
            if (balanceFromJson == null) {
                throw new InvalidRequestException("Не указан баланс аккаунта");
            }
            BigDecimal accountStartBalance = new BigDecimal(balanceFromJson);
            Account account = accountService.crateAccount(accountStartBalance);
            JsonObject responseBody = new JsonObject();
            responseBody.put("number", account.getNumber());
            responseBody.put("balance", account.getBalance().doubleValue());
            routingContext.response().end(responseBody.encode());
        };
    }

    public Handler<RoutingContext> getAccountNumberHandler() {
        return routingContext -> {
            String number = routingContext.request().getParam("number");
            Long accountNumber = Long.valueOf(number);
            Account account = accountService.getAccount(accountNumber);
            JsonObject responseBody = new JsonObject();
            responseBody.put("number", account.getNumber());
            responseBody.put("balance", account.getBalance().doubleValue());
            routingContext.response().end(responseBody.encode());
        };
    }
}
