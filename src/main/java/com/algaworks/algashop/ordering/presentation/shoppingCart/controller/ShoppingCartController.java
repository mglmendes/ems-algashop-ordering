package com.algaworks.algashop.ordering.presentation.shoppingCart.controller;


import com.algaworks.algashop.ordering.core.application.model.shoppingcart.service.ShoppingCartManagementApplicationService;
import com.algaworks.algashop.ordering.core.domain.model.customer.exception.CustomerNotFoundException;
import com.algaworks.algashop.ordering.core.domain.model.product.exception.ProductNotFoundException;
import com.algaworks.algashop.ordering.core.ports.in.shoppingcart.ForQueryingShoppingCarts;
import com.algaworks.algashop.ordering.core.ports.in.shoppingcart.input.ShoppingCartItemInput;
import com.algaworks.algashop.ordering.core.ports.in.shoppingcart.output.ShoppingCartOutput;
import com.algaworks.algashop.ordering.presentation.exception.UnprocessableEntityException;
import com.algaworks.algashop.ordering.presentation.shoppingCart.input.ShoppingCartInput;
import com.algaworks.algashop.ordering.presentation.shoppingCart.model.ShoppingCartItemListModel;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequestMapping("/api/v1/shopping-carts")
@RequiredArgsConstructor
public class ShoppingCartController {

	private final ShoppingCartManagementApplicationService managementService;
	private final ForQueryingShoppingCarts forQueryingShoppingCarts;

	@PostMapping
	@ResponseStatus(HttpStatus.CREATED)
	public ShoppingCartOutput create(@RequestBody @Valid ShoppingCartInput input) {
		UUID shoppingCartId;
		try {
			shoppingCartId = managementService.createNew(input.getCustomerId());
		} catch (CustomerNotFoundException e) {
			throw new UnprocessableEntityException(e.getMessage(), e);
		}
		return forQueryingShoppingCarts.findById(shoppingCartId);
	}

	@GetMapping("/{shoppingCartId}")
	public ShoppingCartOutput getById(@PathVariable UUID shoppingCartId) {
		return forQueryingShoppingCarts.findById(shoppingCartId);
	}

	@GetMapping("/{shoppingCartId}/items")
	public ShoppingCartItemListModel getItems(@PathVariable UUID shoppingCartId) {
		var items = forQueryingShoppingCarts.findById(shoppingCartId).getItems();
		return new ShoppingCartItemListModel(items);
	}

	@DeleteMapping("/{shoppingCartId}")
	@ResponseStatus(HttpStatus.NO_CONTENT)
	public void delete(@PathVariable UUID shoppingCartId) {
		managementService.delete(shoppingCartId);
	}

	@DeleteMapping("/{shoppingCartId}/items")
	@ResponseStatus(HttpStatus.NO_CONTENT)
	public void empty(@PathVariable UUID shoppingCartId) {
		managementService.empty(shoppingCartId);
	}

	@PostMapping("/{shoppingCartId}/items")
	@ResponseStatus(HttpStatus.NO_CONTENT)
	public void addItem(@PathVariable UUID shoppingCartId,
		   			    @RequestBody @Valid ShoppingCartItemInput input) {
		input.setShoppingCartId(shoppingCartId);
		try {
			managementService.addItem(input);
		} catch (ProductNotFoundException e) {
			throw new UnprocessableEntityException(e.getMessage(), e);
		}
	}

	@DeleteMapping("/{shoppingCartId}/items/{itemId}")
	@ResponseStatus(HttpStatus.NO_CONTENT)
	public void removeItem(@PathVariable UUID shoppingCartId,
						   @PathVariable UUID itemId) {
		managementService.removeItem(shoppingCartId, itemId);
	}
}