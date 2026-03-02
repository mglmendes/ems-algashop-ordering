package com.algaworks.algashop.ordering.presentation.order;

import com.algaworks.algashop.ordering.application.model.customer.service.CustomerManagementApplicationService;
import com.algaworks.algashop.ordering.domain.model.customer.CustomerPersistenceEntityTestDataBuilder;
import com.algaworks.algashop.ordering.domain.model.customer.entity.Customer;
import com.algaworks.algashop.ordering.infrastructure.persistence.customer.repository.CustomerPersistenceEntityRepository;
import com.algaworks.algashop.ordering.utils.AlgaShopResourceUtils;
import io.restassured.RestAssured;
import io.restassured.config.JsonConfig;
import io.restassured.http.ContentType;
import io.restassured.path.json.config.JsonPathConfig;
import org.aspectj.lang.annotation.Before;
import org.hamcrest.Matchers;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;

import java.util.UUID;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class OrderControllerIT {

    @LocalServerPort
    private int port;

    private static boolean databaseInitialized;

    @Autowired
    private CustomerPersistenceEntityRepository customerRepository;

    private static final UUID validCustomerId = UUID.fromString("6e148bd5-47f6-4022-b9da-07cfaa294f7a");

    @BeforeEach
    void setUp() {
        RestAssured.enableLoggingOfRequestAndResponseIfValidationFails();
        RestAssured.port = port;

        JsonConfig jsonConfig = JsonConfig.jsonConfig().numberReturnType(JsonPathConfig.NumberReturnType.BIG_DECIMAL);

        RestAssured.config().jsonConfig(jsonConfig);

        initDatabase();
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
                .statusCode(HttpStatus.UNPROCESSABLE_ENTITY.value())
                .body("id", Matchers.not(Matchers.emptyString()),
                        "customer.id", Matchers.is(validCustomerId.toString()));

    }
}
