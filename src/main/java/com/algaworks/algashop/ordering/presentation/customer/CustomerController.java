package com.algaworks.algashop.ordering.presentation.customer;

import com.algaworks.algashop.ordering.application.model.customer.filter.CustomerFilter;
import com.algaworks.algashop.ordering.application.model.customer.input.CustomerInput;
import com.algaworks.algashop.ordering.application.model.customer.input.CustomerUpdateInput;
import com.algaworks.algashop.ordering.application.model.customer.output.CustomerOutput;
import com.algaworks.algashop.ordering.application.model.customer.output.CustomerSummaryOutput;
import com.algaworks.algashop.ordering.application.model.customer.query.CustomerQueryService;
import com.algaworks.algashop.ordering.application.model.customer.service.CustomerManagementApplicationService;
import com.algaworks.algashop.ordering.presentation.model.PageModel;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.method.annotation.MvcUriComponentsBuilder;
import org.springframework.web.util.UriComponentsBuilder;

import java.util.UUID;

@RestController
@RequestMapping("/api/v1/customers")
@RequiredArgsConstructor
public class CustomerController {

    private final CustomerManagementApplicationService customerApplicationService;
    private final CustomerQueryService customerQueryService;

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public CustomerOutput create(@RequestBody @Valid CustomerInput customerInput, HttpServletResponse response) {
        UUID customerId = customerApplicationService.create(customerInput);
        UriComponentsBuilder builder = MvcUriComponentsBuilder.fromMethodCall(
                MvcUriComponentsBuilder.on(CustomerController.class)
                        .findById(customerId)
        );
        response.addHeader("Location", builder.toUriString());
        return customerQueryService.findById(customerId);
    }

    @GetMapping
    public PageModel<CustomerSummaryOutput> findAll(CustomerFilter customerFilter) {
        return PageModel.of(customerQueryService.filter(customerFilter));
    }

    @GetMapping("/{customerId}")
    public CustomerOutput findById(@PathVariable("customerId") UUID customerId) {
        return customerQueryService.findById(customerId);
    }

    @PutMapping("/{customerId}")
    public CustomerOutput update(@PathVariable UUID customerId,
                                 @RequestBody @Valid CustomerUpdateInput input) {
        customerApplicationService.update(customerId, input);
        return customerQueryService.findById(customerId);
    }

    @DeleteMapping("/{customerId}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void delete(@PathVariable UUID customerId) {
        customerApplicationService.archive(customerId);
    }
}
