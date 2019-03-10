package com.dabanin.moneytransfer.server;

import com.dabanin.moneytransfer.controller.AccountController;
import com.dabanin.moneytransfer.controller.ExceptionHandler;
import com.dabanin.moneytransfer.controller.TransferController;
import com.dabanin.moneytransfer.di.DependencyContext;
import com.google.inject.Guice;
import com.google.inject.Injector;
import io.vertx.core.AbstractVerticle;
import io.vertx.ext.web.Router;
import io.vertx.ext.web.handler.BodyHandler;

public class Server extends AbstractVerticle {

    public void start() {
        Router router = Router.router(vertx);

        router.route()
                .handler(BodyHandler.create())
                .consumes("application/json")
                .produces("application/json");

        Injector injector = Guice.createInjector(new DependencyContext());
        AccountController accountController = injector.getInstance(AccountController.class);
        TransferController transferController = injector.getInstance(TransferController.class);
        ExceptionHandler exceptionHandler = new ExceptionHandler();

        router.route().failureHandler(exceptionHandler.getExceptionHandler());

        router.post("/account").handler(accountController.createAccountHandler());
        router.get("/account/:number").handler(accountController.getAccountNumberHandler());
        router.post("/transfer").handler(transferController.doTransferHandler());

        vertx.createHttpServer()
                .requestHandler(router)
                .listen(8080);
    }
}

