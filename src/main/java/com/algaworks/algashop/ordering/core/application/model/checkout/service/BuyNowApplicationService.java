package com.algaworks.algashop.ordering.core.application.model.checkout.service;

import com.algaworks.algashop.ordering.core.application.model.checkout.disassembler.BillingInputDisassembler;
import com.algaworks.algashop.ordering.core.application.model.checkout.disassembler.ShippingInputDisassembler;
import com.algaworks.algashop.ordering.core.ports.in.order.input.BuyNowInput;
import com.algaworks.algashop.ordering.core.ports.in.order.input.ShippingInput;
import com.algaworks.algashop.ordering.core.domain.model.common.Quantity;
import com.algaworks.algashop.ordering.core.domain.model.common.ZipCode;
import com.algaworks.algashop.ordering.core.domain.model.customer.entity.Customer;
import com.algaworks.algashop.ordering.core.domain.model.customer.exception.CustomerNotFoundException;
import com.algaworks.algashop.ordering.core.domain.model.customer.repository.Customers;
import com.algaworks.algashop.ordering.core.domain.model.customer.valueobjects.CustomerId;
import com.algaworks.algashop.ordering.core.domain.model.generic.DomainException;
import com.algaworks.algashop.ordering.core.domain.model.order.entity.Order;
import com.algaworks.algashop.ordering.core.domain.model.order.entity.enums.PaymentMethod;
import com.algaworks.algashop.ordering.core.domain.model.order.repository.Orders;
import com.algaworks.algashop.ordering.core.domain.model.order.service.BuyNowService;
import com.algaworks.algashop.ordering.core.domain.model.order.shipping.OriginAddressService;
import com.algaworks.algashop.ordering.core.domain.model.order.shipping.ShippingCostService;
import com.algaworks.algashop.ordering.core.domain.model.order.valueobjects.Billing;
import com.algaworks.algashop.ordering.core.domain.model.order.valueobjects.CreditCardId;
import com.algaworks.algashop.ordering.core.domain.model.order.valueobjects.Shipping;
import com.algaworks.algashop.ordering.core.domain.model.product.exception.ProductNotFoundException;
import com.algaworks.algashop.ordering.core.domain.model.product.service.ProductCatalogService;
import com.algaworks.algashop.ordering.core.domain.model.product.valueobject.Product;
import com.algaworks.algashop.ordering.core.domain.model.product.valueobject.ProductId;
import com.algaworks.algashop.ordering.core.ports.in.order.ForBuyingProduct;

import java.util.Objects;

public class BuyNowApplicationService implements ForBuyingProduct {

    private final BuyNowService buyNowService;
    private final ProductCatalogService productCatalogService;

    private final ShippingCostService shippingCostService;
    private final OriginAddressService originAddressService;

    private final Orders orders;
    private final Customers customers;

    private final ShippingInputDisassembler shippingInputDisassembler;
    private final BillingInputDisassembler billingInputDisassembler;

    public BuyNowApplicationService(BuyNowService buyNowService, ProductCatalogService productCatalogService,
                                    ShippingCostService shippingCostService, OriginAddressService originAddressService,
                                    Orders orders, Customers customers,
                                    ShippingInputDisassembler shippingInputDisassembler,
                                    BillingInputDisassembler billingInputDisassembler) {
        this.buyNowService = buyNowService;
        this.productCatalogService = productCatalogService;
        this.shippingCostService = shippingCostService;
        this.originAddressService = originAddressService;
        this.orders = orders;
        this.customers = customers;
        this.shippingInputDisassembler = shippingInputDisassembler;
        this.billingInputDisassembler = billingInputDisassembler;
    }

    //    @Transactional
    @Override
    public String buyNow(BuyNowInput input) {
        Objects.requireNonNull(input);

        PaymentMethod paymentMethod = PaymentMethod.valueOf(input.getPaymentMethod());
        CustomerId customerId = new CustomerId(input.getCustomerId());
        ProductId productId = new ProductId(input.getProductId());
        Quantity quantity = new Quantity(input.getQuantity());
        CreditCardId creditCardId = null;

        if (PaymentMethod.CREDIT_CARD.equals(paymentMethod)) {
            if (input.getCreditCardId() == null) {
                throw new DomainException("Credit card id is required");
            }
            creditCardId = new CreditCardId(input.getCreditCardId());
        }

        Customer customer = customers.ofId(customerId).orElseThrow(
                CustomerNotFoundException::new
        );
        Product product = productCatalogService.ofId(productId).orElseThrow(
                () -> new ProductNotFoundException(productId.value())
        );
        var shippingCalculationResult = calculateShippingCost(input.getShipping());

        Shipping shipping = shippingInputDisassembler.toDomainModel(input.getShipping(), shippingCalculationResult);
        Billing billing = billingInputDisassembler.toDomainModel(input.getBilling());


        Order order = buyNowService.buyNow(
                product,
                customer,
                billing,
                shipping,
                quantity,
                paymentMethod,
                creditCardId
        );

        orders.add(order);

        return order.id().toString();
    }

    private ShippingCostService.CalculationResult calculateShippingCost(ShippingInput shipping) {
        ZipCode originZipCode = originAddressService.originAddress().zipCode();
        ZipCode destinationZipCode = new ZipCode(shipping.getAddress().getZipCode());

        return shippingCostService.calculate(new ShippingCostService.CalculationRequest(
                originZipCode,
                destinationZipCode));

    }
}
