package com.algaworks.algashop.ordering.application.model.checkout.service;

import com.algaworks.algashop.ordering.application.model.checkout.disassembler.BillingInputDisassembler;
import com.algaworks.algashop.ordering.application.model.checkout.disassembler.ShippingInputDisassembler;
import com.algaworks.algashop.ordering.application.model.checkout.input.CheckoutInput;
import com.algaworks.algashop.ordering.domain.model.commons.ZipCode;
import com.algaworks.algashop.ordering.domain.model.order.entity.Order;
import com.algaworks.algashop.ordering.domain.model.order.entity.enums.PaymentMethod;
import com.algaworks.algashop.ordering.domain.model.order.repository.Orders;
import com.algaworks.algashop.ordering.domain.model.order.service.CheckoutService;
import com.algaworks.algashop.ordering.domain.model.order.shipping.OriginAddressService;
import com.algaworks.algashop.ordering.domain.model.order.shipping.ShippingCostService;
import com.algaworks.algashop.ordering.domain.model.order.valueobjects.Billing;
import com.algaworks.algashop.ordering.domain.model.order.valueobjects.Shipping;
import com.algaworks.algashop.ordering.domain.model.shoppingcart.entity.ShoppingCart;
import com.algaworks.algashop.ordering.domain.model.shoppingcart.exception.ShoppingCartNotFoundException;
import com.algaworks.algashop.ordering.domain.model.shoppingcart.repository.ShoppingCarts;
import com.algaworks.algashop.ordering.domain.model.shoppingcart.valueobject.ShoppingCartId;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Objects;

@Service
@RequiredArgsConstructor
public class CheckoutApplicationService {

    private final ShoppingCarts shoppingCarts;

    private final CheckoutService checkoutService;

    private final BillingInputDisassembler billingInputDisassembler;

    private final ShippingInputDisassembler shippingInputDisassembler;

    private final ShippingCostService shippingCostService;

    private final OriginAddressService originAddressService;

    private final Orders orders;

    @Transactional
    public String checkout(CheckoutInput input) {
        Objects.requireNonNull(input);
        ShoppingCart shoppingCart = shoppingCarts.ofId(new ShoppingCartId(input.getShoppingCartId())).orElseThrow(
                () -> new ShoppingCartNotFoundException(input.getShoppingCartId())
        );

        Billing billingInfo = billingInputDisassembler.toDomainModel(input.getBilling());

        Shipping shippingInfo = shippingInputDisassembler.toDomainModel(
                input.getShipping(),
                calculateShippingCost(input));

        Order checkoutedOrder = checkoutService.checkout(
                shoppingCart,
                billingInfo,
                shippingInfo,
                PaymentMethod.valueOf(input.getPaymentMethod())
        );


        orders.add(checkoutedOrder);
        shoppingCarts.add(shoppingCart);

        return checkoutedOrder.id().toString();
    }

    private ShippingCostService.CalculationResult calculateShippingCost(CheckoutInput input) {
        return shippingCostService.calculate(new ShippingCostService.CalculationRequest(
                originAddressService.originAddress().zipCode(),
                new ZipCode(input.getShipping().getAddress().getZipCode())));
    }
}
