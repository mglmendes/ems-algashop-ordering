package com.algaworks.algashop.ordering.presentation.order;

import com.algaworks.algashop.ordering.domain.model.customer.CustomerPersistenceEntityTestDataBuilder;
import com.algaworks.algashop.ordering.infrastructure.persistence.customer.repository.CustomerPersistenceEntityRepository;
import com.algaworks.algashop.ordering.utils.AlgaShopResourceUtils;
import com.github.tomakehurst.wiremock.WireMockServer;
import com.github.tomakehurst.wiremock.extension.responsetemplating.ResponseTemplateTransformer;
import io.restassured.RestAssured;
import io.restassured.config.JsonConfig;
import io.restassured.http.ContentType;
import io.restassured.path.json.config.JsonPathConfig;
import org.hamcrest.Matchers;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;

import java.util.UUID;

import static com.github.tomakehurst.wiremock.core.WireMockConfiguration.options;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class OrderControllerIT {

    @LocalServerPort
    private int port;

    private static boolean databaseInitialized;

    @Autowired
    private CustomerPersistenceEntityRepository customerRepository;

    private static final UUID validCustomerId = UUID.fromString("6e148bd5-47f6-4022-b9da-07cfaa294f7a");

    private WireMockServer wireMockServerProductCatalog;
    private WireMockServer wireMockServerRapidex;

    @BeforeEach
    void setUp() {
        RestAssured.enableLoggingOfRequestAndResponseIfValidationFails();
        RestAssured.port = port;

        JsonConfig jsonConfig = JsonConfig.jsonConfig().numberReturnType(JsonPathConfig.NumberReturnType.BIG_DECIMAL);

        RestAssured.config().jsonConfig(jsonConfig);

        initDatabase();

        wireMockServerProductCatalog = new WireMockServer(
                options().port(8781)
                        .usingFilesUnderDirectory("src/test/resources/wiremock/product-catalog")
                        .extensions(new ResponseTemplateTransformer(true))
        );

        wireMockServerRapidex = new WireMockServer(
                options().port(8780)
                        .usingFilesUnderDirectory("src/test/resources/wiremock/rapidex")
                        .extensions(new ResponseTemplateTransformer(true))
        );

        wireMockServerProductCatalog.start();
        wireMockServerRapidex.start();
    }

    @AfterEach
    void tearDown() {
        wireMockServerProductCatalog.stop();
        wireMockServerRapidex.stop();
    }

    private void initDatabase() {
        if (databaseInitialized) {
            return;
        }

        customerRepository.saveAndFlush(CustomerPersistenceEntityTestDataBuilder.aCustomer().id(validCustomerId).build());

        databaseInitialized = true;
    }

    @Test
    public void shouldCreateOrderUsingProduct() {
        String jsonBody = AlgaShopResourceUtils.readContent("json/create-order-with-product.json");
        RestAssured.given()
                .accept(MediaType.APPLICATION_JSON_VALUE)
                .contentType("application/vnd.order-with-product.v1+json")
                .body(jsonBody)
                .when()
                .post("/api/v1/orders")
                .then()
                .assertThat()
                .contentType(ContentType.JSON)
                .statusCode(HttpStatus.CREATED.value());

    }

    @Test
    public void shouldThrowExceptionWhenCreateOrderUsingProductWithCustomerNotFound() {
        String jsonBody = AlgaShopResourceUtils.readContent("json/create-order-with-product-invalid-customer.json");
        RestAssured.given()
                .accept(MediaType.APPLICATION_JSON_VALUE)
                .contentType("application/vnd.order-with-product.v1+json")
                .body(jsonBody)
                .when()
                .post("/api/v1/orders")
                .then()
                .assertThat()
                .contentType(MediaType.APPLICATION_PROBLEM_JSON_VALUE)
                .statusCode(HttpStatus.UNPROCESSABLE_ENTITY.value());

    }
}
