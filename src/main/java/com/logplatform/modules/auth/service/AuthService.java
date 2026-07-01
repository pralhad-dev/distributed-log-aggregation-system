package com.logplatform.modules.auth.service;

import com.logplatform.common.client.LogClient;
import com.logplatform.modules.auth.dto.AuthRequest;
import com.logplatform.modules.auth.dto.AuthResponse;
import com.logplatform.modules.auth.entity.User;
import com.logplatform.modules.auth.repository.UserRepository;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.UUID;

@Service
public class AuthService {

  private final UserRepository userRepository;
  private final LogClient logClient;

  public AuthService(UserRepository userRepository, LogClient logClient) {
    this.userRepository = userRepository;
    this.logClient = logClient;
  }

  public AuthResponse register(AuthRequest request) {

    String traceId = UUID.randomUUID().toString();

    logClient.sendLog("auth-service", "INFO", "User registration started");

    try {

      User user = new User();
      user.setUsername(request.username());
      user.setPassword(request.password()); // (NOTE: later hash karna)
      user.setRole("USER");
      user.setCreatedAt(LocalDateTime.now());

      userRepository.save(user);

      logClient.sendLog("auth-service", "INFO", "User registered successfully");

      return new AuthResponse("SUCCESS", "User created");

    } catch (Exception e) {

      logClient.sendLog("auth-service", "ERROR", e.getMessage());

      return new AuthResponse("ERROR", "Registration failed");
    }
  }
}