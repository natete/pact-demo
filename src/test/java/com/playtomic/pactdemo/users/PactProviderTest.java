package com.playtomic.pactdemo.users;

import static com.github.tomakehurst.wiremock.client.WireMock.aResponse;
import static com.github.tomakehurst.wiremock.client.WireMock.get;
import static com.github.tomakehurst.wiremock.client.WireMock.urlEqualTo;

import au.com.dius.pact.provider.junit5.HttpTestTarget;
import au.com.dius.pact.provider.junit5.PactVerificationContext;
import au.com.dius.pact.provider.junitsupport.Provider;
import au.com.dius.pact.provider.junitsupport.State;
import au.com.dius.pact.provider.junitsupport.loader.PactBroker;
import au.com.dius.pact.provider.spring.junit5.PactVerificationSpringProvider;
import com.atlassian.ta.wiremockpactgenerator.WireMockPactGenerator;
import com.github.tomakehurst.wiremock.WireMockServer;
import com.playtomic.pactdemo.users.dao.UsersRepository;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.TestTemplate;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit.jupiter.SpringExtension;

@ExtendWith(SpringExtension.class)
@SpringBootTest(webEnvironment = WebEnvironment.RANDOM_PORT)
@Provider("UsersService")
//@PactFolder("pacts")
@PactBroker
@ActiveProfiles("test")
public class PactProviderTest {

    @LocalServerPort
    private int serverPort;

    WireMockServer wireMockServer;

    @Autowired
    private UsersRepository usersRepository;

    @TestTemplate
    @ExtendWith(PactVerificationSpringProvider.class)
    void pactVerificationTestTemplate(PactVerificationContext context) {
        context.verifyInteraction();
    }

    @BeforeEach
    public void setUp(PactVerificationContext context) {
        context.setTarget(new HttpTestTarget("localhost", serverPort));

        wireMockServer = new WireMockServer(10100);
        wireMockServer.addMockServiceRequestListener(
            WireMockPactGenerator
                .builder("UsersService", "CarsService")
                .build()
        );

        wireMockServer.addStubMapping(
            get(urlEqualTo("/v1/cars/1"))
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
        usersRepository.clear();
    }

    @State("user with id 1 exists")
    public void getUserById_OK() {
        usersRepository.save("tom");
    }

    @State("user with id 1 exists and it has cars")
    public void saveUser() {
        usersRepository.save("tom");
    }

    @State("user with id 666 does not exist")
    public void getUserById_404() {
    }
}
