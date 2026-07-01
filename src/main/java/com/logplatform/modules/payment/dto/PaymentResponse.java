package com.logplatform.modules.payment.dto;

public record PaymentResponse(
    String status,
    String message
) {}