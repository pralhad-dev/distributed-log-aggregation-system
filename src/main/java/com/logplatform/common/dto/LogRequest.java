package com.logplatform.common.dto;

public record LogRequest(

    Long Id,
    String serviceName,

    String level,

    String message,

    String traceId

) {}