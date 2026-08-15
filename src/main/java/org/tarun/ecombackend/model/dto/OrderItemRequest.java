package org.tarun.ecombackend.model.dto;

public record OrderItemRequest(
        int productId,
        int quantity
) {}
