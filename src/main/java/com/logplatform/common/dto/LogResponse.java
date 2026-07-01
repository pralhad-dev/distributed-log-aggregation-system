package com.logplatform.common.dto;

import java.time.LocalDateTime;

public record LogResponse(

    Long id,
    String serviceName,
    String level,
    String message,
    String traceId,
    LocalDateTime createdAt

) {}