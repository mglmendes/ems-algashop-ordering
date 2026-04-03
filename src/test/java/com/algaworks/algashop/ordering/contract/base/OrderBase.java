package com.algaworks.algashop.ordering.contract.base;

import com.algaworks.algashop.ordering.core.ports.in.order.ForBuyingWithShoppingCart;
import com.algaworks.algashop.ordering.core.ports.in.order.ForQueryingOrders;
import com.algaworks.algashop.ordering.core.ports.in.order.input.BuyNowInput;
import com.algaworks.algashop.ordering.core.ports.in.order.input.CheckoutInput;
import com.algaworks.algashop.ordering.core.ports.in.order.ForBuyingProduct;
import com.algaworks.algashop.ordering.core.application.model.order.databuilder.OrderSummaryOutputTestDataBuilder;
import com.algaworks.algashop.ordering.core.ports.in.order.filter.OrderFilter;
import com.algaworks.algashop.ordering.core.domain.model.order.exceptions.OrderNotFoundException;
import com.algaworks.algashop.ordering.infrastructure.adapters.in.web.order.OrderController;
import io.restassured.module.mockmvc.RestAssuredMockMvc;
import org.junit.jupiter.api.BeforeEach;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.data.domain.PageImpl;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import java.nio.charset.StandardCharsets;
import java.util.List;

@WebMvcTest(controllers = OrderController.class)
public class OrderBase {

    @Autowired
    private WebApplicationContext context;

    @MockitoBean
    private ForQueryingOrders forQueryingOrders;

    @MockitoBean
    private ForBuyingProduct buyNowApplicationService;

    @MockitoBean
    private ForBuyingWithShoppingCart checkoutApplicationService;

    public static final String notFoundOrderId = "91226N0693HDH";
    public static final String validOrderId = "01226N0640J7Q";

    @BeforeEach
    void setUp() {
        RestAssuredMockMvc.mockMvc(
                MockMvcBuilders.webAppContextSetup(context)
                        .defaultResponseCharacterEncoding(StandardCharsets.UTF_8)
                        .build()
        );

        RestAssuredMockMvc.enableLoggingOfRequestAndResponseIfValidationFails();

        Mockito.when(buyNowApplicationService.buyNow(Mockito.any(BuyNowInput.class)))
                .thenReturn(validOrderId);

        Mockito.when(checkoutApplicationService.checkout(Mockito.any(CheckoutInput.class)))
                .thenReturn(validOrderId);

        Mockito.when(forQueryingOrders.findById(validOrderId))
                .thenReturn(OrderDetailOutputTestDataBuilder.placedOrder(validOrderId).build());

        Mockito.when(forQueryingOrders.findById(notFoundOrderId))
                .thenThrow(new OrderNotFoundException(notFoundOrderId));

        Mockito.when(forQueryingOrders.filter(Mockito.any(OrderFilter.class)))
                .thenReturn(new PageImpl<>(
                        List.of(OrderSummaryOutputTestDataBuilder.placedOrder().id(validOrderId).build())
                ));
    }

}
