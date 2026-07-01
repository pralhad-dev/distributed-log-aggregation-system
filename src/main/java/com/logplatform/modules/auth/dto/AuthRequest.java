package com.logplatform.modules.auth.dto;

public record AuthRequest(
    String username,
    String password
) {}