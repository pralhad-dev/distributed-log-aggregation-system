package com.logplatform.modules.order.dto;

public record OrderRequest(
    String userId,
    String productName,
    Integer quantity
) {}