package com.dabanin.moneytransfer.controller;

import com.dabanin.moneytransfer.server.Server;
import io.vertx.core.Vertx;
import io.vertx.core.json.JsonObject;
import io.vertx.ext.web.client.WebClient;
import io.vertx.ext.web.codec.BodyCodec;
import io.vertx.junit5.VertxExtension;
import io.vertx.junit5.VertxTestContext;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;

@ExtendWith(VertxExtension.class)
class TransferControllerTest {

    @Test
    void transferTest(Vertx vertx, VertxTestContext testContext) {
        WebClient webClient = WebClient.create(vertx);
        vertx.deployVerticle(new Server(),
                testContext.succeeding(handler -> {
                    JsonObject requestJson = new JsonObject();
                    requestJson.put("from", 0);
                    requestJson.put("to", 1);
                    requestJson.put("amount", 1000.0);

                    webClient.post(8080, "localhost", "/account")
                            .as(BodyCodec.jsonObject())
                            .sendJson(10000, testContext.succeeding(response -> testContext.verify(() -> {
                                Assertions.assertThat(response.statusCode()).isEqualTo(200);
                                Assertions.assertThat(response.body().getDouble("balance")).isEqualTo(10000.0);
                            })));

                    webClient.post(8080, "localhost", "/account")
                            .as(BodyCodec.jsonObject())
                            .sendJson(10000, testContext.succeeding(response -> testContext.verify(() -> {
                                Assertions.assertThat(response.statusCode()).isEqualTo(200);
                                Assertions.assertThat(response.body().getDouble("balance")).isEqualTo(10000.0);
                            })));

                    webClient.post(8080, "localhost", "/transfer")
                            .as(BodyCodec.jsonObject())
                            .sendJsonObject(requestJson, testContext.succeeding(createResponse -> testContext.verify(() -> {
                                Assertions.assertThat(createResponse.statusCode()).isEqualTo(200);
                                Assertions.assertThat(createResponse.body().getLong("from")).isEqualTo(0);
                                Assertions.assertThat(createResponse.body().getLong("to")).isEqualTo(1);
                                Assertions.assertThat(createResponse.body().getDouble("amount")).isEqualTo(1000.0);
                            })));

                    webClient.get(8080, "localhost", "/account/0")
                            .as(BodyCodec.jsonObject())
                            .send(testContext.succeeding(getResponse -> testContext.verify(() -> {
                                Assertions.assertThat(getResponse.statusCode()).isEqualTo(200);
                                Assertions.assertThat(getResponse.body().getLong("number")).isEqualTo(0L);
                                Assertions.assertThat(getResponse.body().getDouble("balance")).isEqualTo(9000.0);
                            })));

                    webClient.get(8080, "localhost", "/account/1")
                            .as(BodyCodec.jsonObject())
                            .send(testContext.succeeding(getResponse -> testContext.verify(() -> {
                                Assertions.assertThat(getResponse.statusCode()).isEqualTo(200);
                                Assertions.assertThat(getResponse.body().getLong("number")).isEqualTo(1L);
                                Assertions.assertThat(getResponse.body().getDouble("balance")).isEqualTo(11000.0);
                                testContext.completeNow();
                            })));
                }));
    }

}
