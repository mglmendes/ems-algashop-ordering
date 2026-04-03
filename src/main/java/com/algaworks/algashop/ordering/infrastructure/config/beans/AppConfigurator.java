package com.algaworks.algashop.ordering.infrastructure.config.beans;

import com.algaworks.algashop.ordering.core.application.model.checkout.disassembler.BillingInputDisassembler;
import com.algaworks.algashop.ordering.core.application.model.checkout.disassembler.ShippingInputDisassembler;
import com.algaworks.algashop.ordering.core.application.model.checkout.service.BuyNowApplicationService;
import com.algaworks.algashop.ordering.core.domain.model.customer.repository.Customers;
import com.algaworks.algashop.ordering.core.domain.model.order.repository.Orders;
import com.algaworks.algashop.ordering.core.domain.model.order.service.BuyNowService;
import com.algaworks.algashop.ordering.core.domain.model.order.shipping.OriginAddressService;
import com.algaworks.algashop.ordering.core.domain.model.order.shipping.ShippingCostService;
import com.algaworks.algashop.ordering.core.domain.model.product.service.ProductCatalogService;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class AppConfigurator {

    @Bean
    public BuyNowApplicationService buyNowApplicationService(BuyNowService buyNowService,
                                                             ProductCatalogService productCatalogService,
                                                             ShippingCostService shippingCostService,
                                                             OriginAddressService originAddressService,
                                                             Orders orders, Customers customers,
                                                             ShippingInputDisassembler shippingInputDisassembler,
                                                             BillingInputDisassembler billingInputDisassembler) {
        return new BuyNowApplicationService(buyNowService, productCatalogService, shippingCostService,
                originAddressService, orders, customers, shippingInputDisassembler, billingInputDisassembler);
    }
}
