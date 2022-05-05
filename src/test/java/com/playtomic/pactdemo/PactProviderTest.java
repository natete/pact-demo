package com.playtomic.pactdemo;

import static com.github.tomakehurst.wiremock.client.WireMock.aResponse;
import static com.github.tomakehurst.wiremock.client.WireMock.get;
import static com.github.tomakehurst.wiremock.client.WireMock.urlEqualTo;

import au.com.dius.pact.provider.junit5.PactVerificationContext;
import au.com.dius.pact.provider.junitsupport.Provider;
import au.com.dius.pact.provider.junitsupport.State;
import au.com.dius.pact.provider.junitsupport.loader.PactFolder;
import au.com.dius.pact.provider.spring.junit5.PactVerificationSpringProvider;
import com.atlassian.ta.wiremockpactgenerator.WireMockPactGenerator;
import com.github.tomakehurst.wiremock.WireMockServer;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.TestTemplate;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.test.context.junit.jupiter.SpringExtension;

@ExtendWith(SpringExtension.class)
@SpringBootTest(webEnvironment = WebEnvironment.DEFINED_PORT)
@Provider("test_provider")
@PactFolder("pacts")
public class PactProviderTest {

    WireMockServer wireMockServer;

    @TestTemplate
    @ExtendWith(PactVerificationSpringProvider.class)
    void pactVerificationTestTemplate(PactVerificationContext context) {
        context.verifyInteraction();
    }

    @BeforeEach
    public void setUp() {
        wireMockServer = new WireMockServer(10100);
        wireMockServer.addMockServiceRequestListener(
            WireMockPactGenerator
                .builder("cars-consumer", "cars-provider")
                .build()
        );

        wireMockServer.addStubMapping(
            get(urlEqualTo("/cars/1"))
                .willReturn(aResponse()
                                .withBody("""
                                                [
                                                    {
                                                        "maker": "Seat",
                                                        "model": "Panda"
                                                    }
                                                ]
                                              """)
                                .withHeader("Content-Type", "application/json")
                                .withStatus(200))
                .build());

        wireMockServer.start();

    }

    @AfterEach
    public void tearDown() {
        wireMockServer.stop();
    }

    @State("test GET")
    public void toGetState() {
    }

    @State("test POST")
    public void toPostState() {
    }

    @State("test GET one user by id")
    public void toGetOneState() {
    }

    @State("test GET one user by id with cars")
    public void toGetOneWithCarsState() {
    }
}
