package com.algaworks.algashop.ordering.domain.model.generic;

public class ErrorMessages {



    private ErrorMessages() {

    }
    public static final String VALIDATION_ERROR_BIRTHDATE_MUST_IN_PAST = "BirthDate must be a past date.";
    public static final String ERROR_EMAIL_ALREADY_IN_USE = "Email %s already in use";
    public static final String VALIDATION_ERROR_FULLNAME_IS_NULL = "FullName cannot be null.";
    public static final String VALIDATION_ERROR_FULLNAME_IS_BLANK = "FullName cannot be blank.";

    public static final String VALIDATION_ERROR_EMAIL_IS_INVALID = "Email is invalid.";
    public static final String ERROR_CUSTOMER_ARCHIVED = "Customer is isArchived. Cannot be changed.";

    public static final String ERROR_CUSTOMER_NOT_FOUND = "Customer with id %s not exists.";

    public static final String ERROR_ORDER_STATUS_CANNOT_BE_CHANGED = "Cannot change order %s status from %s to %s";
    public static final String ERROR_ORDER_DOES_NOT_CONTAIN_ODER_ITEM = "Order %s does not contain  item %s";

    public static final String ERROR_PRODUCT_IS_OUT_OF_STOCK = "Product %s is out of stock";
    public static final String ERROR_PRODUCT_NOT_FOUND_EXCEPTION = "Product %s not found";

    public static final String ERROR_ORDER_CANNOT_BE_EDITED = "Order %s with status %s cannot be edited";

    // Order Cannot Be Placed Error Messages
    public static final String ERROR_ORDER_DELIVERY_DATE_CANNOT_BE_IN_THE_PAST =
            "Order %s expected date cannot be in the past";

    public static final String ERROR_ORDER_CANNOT_BE_PLACED_HAS_NO_ITEMS =
            "Order %s cannot be placed, it has no items";

    public static final String ERROR_ORDER_CANNOT_BE_PLACED_HAS_NO_SHIPPING_INFO =
            "Order %s cannot be placed, it has no shipping info";

    public static final String ERROR_ORDER_CANNOT_BE_PLACED_HAS_NO_BILLING_INFO =
            "Order %s cannot be placed, it has no billing info";

    public static final String ERROR_ORDER_CANNOT_BE_PLACED_HAS_NO_PAYMENT_METHOD =
            "Order %s cannot be placed, it has no payment method";

    public static final String ERROR_SHOPPING_CART_DOES_NOT_CONTAIN_ITEM = "Shopping Cart %s does not contain item %s";

    public static final String ERROR_SHOPPING_CART_DOES_NOT_CONTAIN_PRODUCT = "Shopping Cart %s does not contain product %s";

    public static final String ERROR_SHOPPING_CART_ITEM_INCOMPATIBLE_PRODUCT = "Shopping Cart %s cannot be updated, incompatible product %s";

    public static final String ERROR_ORDER_NOT_BELONGS_TO_CUSTOMER = "Order with id %s not belong to customer %s";

    public static final String ERROR_CANNOT_ADD_LOYALTY_POINTS_TO_A_NON_READY_ORDER = "Cannot add Loyalty Points to order %s, non ready order";

    public static final String ERROR_SHOPPING_CART_CANT_PROCEED_TO_CHECKOUT = "Shopping cart has unavailable items. Cannot proceed to checkout";
    public static final String ERROR_CUSTOMER_ALREADY_HAVE_SHOPPING_CART = "Customer with id %s already have shopping cart";
}
