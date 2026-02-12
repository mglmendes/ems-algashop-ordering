package com.algaworks.algashop.ordering.infrastructure.rapidex.client;

import com.algaworks.algashop.ordering.infrastructure.rapidex.dtos.DeliveryCostRequest;
import com.algaworks.algashop.ordering.infrastructure.rapidex.dtos.DeliveryCostResponse;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.service.annotation.PostExchange;


public interface RapiDexAPIClient {

    @PostExchange("/api/delivery-cost")
    DeliveryCostResponse calculate(@RequestBody DeliveryCostRequest request);
}
