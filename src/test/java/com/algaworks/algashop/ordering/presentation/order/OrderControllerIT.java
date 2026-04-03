package com.algaworks.algashop.ordering.presentation.order;

import com.algaworks.algashop.ordering.core.ports.in.order.input.BuyNowInput;
import com.algaworks.algashop.ordering.core.application.model.checkout.service.BuyNowInputTestDataBuilder;
import com.algaworks.algashop.ordering.core.ports.in.order.output.OrderDetailOutput;
import com.algaworks.algashop.ordering.core.domain.model.order.valueobjects.OrderId;
import com.algaworks.algashop.ordering.infrastructure.adapters.out.persistence.customer.repository.CustomerPersistenceEntityRepository;
import com.algaworks.algashop.ordering.infrastructure.adapters.out.persistence.order.repository.OrderPersistenceEntityRepository;
import com.algaworks.algashop.ordering.infrastructure.adapters.out.persistence.shoppingcart.repository.ShoppingCartPersistenceEntityRepository;
import com.algaworks.algashop.ordering.presentation.AbstractPresentationIT;
import com.algaworks.algashop.ordering.utils.AlgaShopResourceUtils;
import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import org.assertj.core.api.Assertions;
import org.hamcrest.Matchers;
import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;

import java.util.UUID;

public class OrderControllerIT extends AbstractPresentationIT {

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
    public void shouldCreateOrderUsingProduct_DTO() {
        BuyNowInput buyNowInput = BuyNowInputTestDataBuilder.aBuyNowInput().productId(validProductId)
                .creditCardId(validCreditCardId).customerId(validCustomerId).build();
        OrderDetailOutput orderDetailOutput = RestAssured.given()
                .accept(MediaType.APPLICATION_JSON_VALUE)
                .contentType("application/vnd.order-with-product.v1+json")
                .body(buyNowInput)
                .when()
                .post("/api/v1/orders")
                .then()
                .assertThat()
                .contentType(ContentType.JSON)
                .statusCode(HttpStatus.CREATED.value())
                .extract().body().as(OrderDetailOutput.class);

        Assertions.assertThat(orderDetailOutput).isNotNull();
        Assertions.assertThat(orderDetailOutput.getCreditCardId()).isEqualTo(validCreditCardId);
    }


    @Test
    public void shouldNotCreateOrderUsingProductWhenProductNotExists() {
        String jsonBody = AlgaShopResourceUtils.readContent("json/create-order-with-invalid-product.json");
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

    @Test
    public void shouldCreateOrderUsingShoppingCart() {

        String json = AlgaShopResourceUtils.readContent("json/create-order-with-shopping-cart.json");

        OrderDetailOutput orderDetailOutput = RestAssured
                .given()
                .accept(MediaType.APPLICATION_JSON_VALUE)
                .contentType("application/vnd.order-with-shopping-cart.v1+json")
                .body(json)
                .when()
                .post("/api/v1/orders")
                .then()
                .assertThat()
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .statusCode(HttpStatus.CREATED.value())
                .body("id", Matchers.not(Matchers.emptyString()),
                        "customer.id", Matchers.is(validCustomerId.toString()))
                .extract()
                .body().as(OrderDetailOutput.class);

        Assertions.assertThat(orderDetailOutput.getCustomer().getId()).isEqualTo(validCustomerId);

        boolean orderExists = orderRepository.existsById(new OrderId(orderDetailOutput.getId()).value().toLong());
        Assertions.assertThat(orderExists).isTrue();
    }
}
