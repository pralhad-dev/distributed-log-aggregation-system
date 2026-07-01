package com.logplatform.modules.payment.dto;

import java.math.BigDecimal;

public record PaymentRequest(
    String userId,
    BigDecimal amount
) {}