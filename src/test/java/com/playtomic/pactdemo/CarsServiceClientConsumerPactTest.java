package com.playtomic.pactdemo;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

import au.com.dius.pact.consumer.MockServer;
import au.com.dius.pact.consumer.dsl.PactDslWithProvider;
import au.com.dius.pact.consumer.junit5.PactConsumerTestExt;
import au.com.dius.pact.consumer.junit5.PactTestFor;
import au.com.dius.pact.core.model.RequestResponsePact;
import au.com.dius.pact.core.model.V4Pact;
import au.com.dius.pact.core.model.annotations.Pact;
import com.playtomic.pactdemo.domain.Car;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.boot.web.client.RestTemplateBuilder;

@ExtendWith(PactConsumerTestExt.class)
@PactTestFor(providerName = "cars-service", port = "10100")
public class CarsServiceClientConsumerPactTest {


    @Pact(provider = "cars-service", consumer = "cars-client")
    public V4Pact getCarByUserId(PactDslWithProvider builder) {
        Map<String, String> headers = new HashMap<>();
        headers.put("Content-Type", "application/json");

        return builder
            .given("test state")
            .uponReceiving("CarsServiceClientConsumerPactTest test interaction")
            .path("/cars/1")
            .method("GET")
            .willRespondWith()
            .status(200)
            .headers(headers)
            .body("""
                        [
                            {
                                "maker": "Seat",
                                "model": "Panda"
                            }
                        ]
                      """)
            .toPact(V4Pact.class);
    }

    @Test
    @PactTestFor(pactMethod = "getCarByUserId")
    void test(MockServer mockServer) {
        Car car = new Car("Seat", "Panda");

        List<Car> expected = List.of(car);

        var restTemplate = new RestTemplateBuilder()
            .rootUri(mockServer.getUrl())
            .build();

        var response = restTemplate.getForEntity("/cars/1", Car[].class);

        var cars = Arrays.asList(Objects.requireNonNull(response.getBody()));

        assertThat(cars).isEqualTo(expected);
    }


}
