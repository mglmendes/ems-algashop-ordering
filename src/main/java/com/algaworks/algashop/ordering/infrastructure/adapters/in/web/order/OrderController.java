package com.algaworks.algashop.ordering.infrastructure.adapters.in.web.order;

import com.algaworks.algashop.ordering.core.ports.in.order.ForBuyingWithShoppingCart;
import com.algaworks.algashop.ordering.core.ports.in.order.input.BuyNowInput;
import com.algaworks.algashop.ordering.core.ports.in.order.input.CheckoutInput;
import com.algaworks.algashop.ordering.core.ports.in.order.ForBuyingProduct;
import com.algaworks.algashop.ordering.core.ports.in.order.ForQueryingOrders;
import com.algaworks.algashop.ordering.core.ports.in.order.filter.OrderFilter;
import com.algaworks.algashop.ordering.core.ports.in.order.output.OrderDetailOutput;
import com.algaworks.algashop.ordering.core.ports.in.order.output.OrderSummaryOutput;
import com.algaworks.algashop.ordering.core.domain.model.customer.exception.CustomerNotFoundException;
import com.algaworks.algashop.ordering.core.domain.model.product.exception.ProductNotFoundException;
import com.algaworks.algashop.ordering.core.domain.model.shoppingcart.exception.ShoppingCartNotFoundException;
import com.algaworks.algashop.ordering.infrastructure.config.exceptionhandler.UnprocessableEntityException;
import com.algaworks.algashop.ordering.infrastructure.adapters.in.web.model.PageModel;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/orders")
@RequiredArgsConstructor
public class OrderController {

    private final ForQueryingOrders forQueryingOrders;
    private final ForBuyingWithShoppingCart checkoutApplicationService;
    private final ForBuyingProduct buyNowApplicationService;

    @GetMapping("/{orderId}")
    public OrderDetailOutput findById(@PathVariable String orderId) {
        return forQueryingOrders.findById(orderId);
    }

    @GetMapping
    public PageModel<OrderSummaryOutput> filter(OrderFilter filter) {
        return PageModel.of(forQueryingOrders.filter(filter));
    }

    @PostMapping(consumes = "application/vnd.order-with-product.v1+json")
    @ResponseStatus(HttpStatus.CREATED)
    public OrderDetailOutput createWithProduct(@Valid @RequestBody BuyNowInput input) {
        String orderId;
        try {
            orderId = buyNowApplicationService.buyNow(input);
        }  catch (CustomerNotFoundException | ProductNotFoundException e) {
            throw new UnprocessableEntityException(e.getMessage(), e);
        }
        return forQueryingOrders.findById(orderId);
    }

    @PostMapping(consumes = "application/vnd.order-with-shopping-cart.v1+json")
    @ResponseStatus(HttpStatus.CREATED)
    public OrderDetailOutput createWithShoppingCart(@Valid @RequestBody CheckoutInput input) {
        String orderId;
        try {
            orderId  = checkoutApplicationService.checkout(input);
        } catch (ShoppingCartNotFoundException e) {
            throw new UnprocessableEntityException(e.getMessage(), e);
        }
        return forQueryingOrders.findById(orderId);
    }
}
