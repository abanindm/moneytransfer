package com.dabanin.moneytransfer.controller;


import com.dabanin.moneytransfer.server.Server;
import io.vertx.core.Vertx;
import io.vertx.ext.web.client.WebClient;
import io.vertx.ext.web.codec.BodyCodec;
import io.vertx.junit5.VertxExtension;
import io.vertx.junit5.VertxTestContext;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;

@ExtendWith(VertxExtension.class)
class AccountControllerTest {

    @Test
    void createAccountTest(Vertx vertx, VertxTestContext testContext) {
        WebClient webClient = WebClient.create(vertx);
        vertx.deployVerticle(new Server(),
                testContext.succeeding(handler -> {

                    webClient.post(8080, "localhost", "/account")
                            .as(BodyCodec.jsonObject())
                            .sendJson(1000L, testContext.succeeding(response -> testContext.verify(() -> {
                                Assertions.assertThat(response.statusCode()).isEqualTo(200);
                                Assertions.assertThat(response.body().getDouble("balance")).isEqualTo(1000.0);
                            })));

                    webClient.post(8080, "localhost", "/account")
                            .as(BodyCodec.jsonObject())
                            .sendJson(-1000L, testContext.succeeding(response -> testContext.verify(() -> {
                                Assertions.assertThat(response.statusCode()).isEqualTo(400);
                                Assertions.assertThat(response.body().getString("message"))
                                        .isEqualTo("Баланс счета не может быть отрицательным");
                                testContext.completeNow();
                            })));
                }));
    }

    @Test
    void getAccountTest(Vertx vertx, VertxTestContext testContext) {
        WebClient webClient = WebClient.create(vertx);
        vertx.deployVerticle(new Server(),
                testContext.succeeding(handler -> {

                    webClient.post(8080, "localhost", "/account")
                            .as(BodyCodec.jsonObject())
                            .sendJson(1000L, testContext.succeeding(createResponse -> testContext.verify(() -> {
                                Assertions.assertThat(createResponse.statusCode()).isEqualTo(200);
                                Assertions.assertThat(createResponse.body().getDouble("balance")).isEqualTo(1000.0);
                            })));

                    webClient.get(8080, "localhost", "/account/0")
                            .as(BodyCodec.jsonObject())
                            .send(testContext.succeeding(getResponse -> testContext.verify(() -> {
                                Assertions.assertThat(getResponse.statusCode()).isEqualTo(200);
                                Assertions.assertThat(getResponse.body().getLong("number")).isEqualTo(0L);
                                Assertions.assertThat(getResponse.body().getDouble("balance")).isEqualTo(1000.0);
                                testContext.completeNow();
                            })));

                    webClient.get(8080, "localhost", "/account/2")
                            .as(BodyCodec.jsonObject())
                            .send(testContext.succeeding(getResponse -> testContext.verify(() -> {
                                Assertions.assertThat(getResponse.statusCode()).isEqualTo(400);
                                Assertions.assertThat(getResponse.body().getString("message"))
                                        .isEqualTo("Аккаунт с номером 2 не существует");
                                testContext.completeNow();
                            })));
                }));

    }
}
