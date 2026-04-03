package com.algaworks.algashop.ordering.presentation.order;

import com.algaworks.algashop.ordering.infrastructure.adapters.out.persistence.customer.repository.CustomerPersistenceEntityRepository;
import com.algaworks.algashop.ordering.infrastructure.persistence.order.repository.OrderPersistenceEntityRepository;
import com.algaworks.algashop.ordering.infrastructure.adapters.out.persistence.shoppingcart.repository.ShoppingCartPersistenceEntityRepository;
import com.algaworks.algashop.ordering.presentation.AbstractPresentationIT;
import com.algaworks.algashop.ordering.utils.AlgaShopResourceUtils;
import io.restassured.RestAssured;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;

import java.util.UUID;

public class OrderControllerWithoutProductCatalogIT extends AbstractPresentationIT {

    @Autowired
    private CustomerPersistenceEntityRepository customerRepository;

    @Autowired
    private OrderPersistenceEntityRepository orderRepository;

    @Autowired
    private ShoppingCartPersistenceEntityRepository shoppingCartRepository;

    private static final UUID validCustomerId = UUID.fromString("6e148bd5-47f6-4022-b9da-07cfaa294f7a");
    private static final UUID validProductId = UUID.fromString("019c90e8-40fa-7ab0-a458-34f237b97987");;
    private static final UUID validCreditCardId = UUID.fromString("4f31582a-66e6-4601-a9d3-ff608c2d4461");

    @BeforeEach
    public void setUp() {
        super.beforeEach();
    }

    @BeforeAll
    public static void setUpAll() {
        AbstractPresentationIT.initWireMock();
    }

    @AfterAll
    public static void afterAll() {
        AbstractPresentationIT.stopMock();
    }

    @Test
    public void shouldNotCreateOrderUsingProductWhenProductAPIIsUnavailable() {
        wireMockProductCatalog.stop();
        String jsonBody = AlgaShopResourceUtils.readContent("json/create-order-with-product.json");
        RestAssured.given()
                .accept(MediaType.APPLICATION_JSON_VALUE)
                .contentType("application/vnd.order-with-product.v1+json")
                .body(jsonBody)
                .when()
                .post("/api/v1/orders")
                .then()
                .assertThat()
                .contentType(MediaType.APPLICATION_PROBLEM_JSON_VALUE)
                .statusCode(HttpStatus.GATEWAY_TIMEOUT.value());

    }
}
