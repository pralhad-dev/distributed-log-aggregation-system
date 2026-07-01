package com.logplatform.common.client;


import com.logplatform.common.dto.LogRequest;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import java.util.UUID;

@Component
public class LogClient {

  private final RestTemplate restTemplate;

  public LogClient(RestTemplate restTemplate) {
    this.restTemplate = restTemplate;
  }

  public void sendLog(String service, String level, String message) {

    LogRequest request = new LogRequest(
        null,
        service,
        level,
        message,
        UUID.randomUUID().toString()
    );

    try {
      restTemplate.postForEntity(
          "http://localhost:8080/logs",
          request,
          Void.class
      );
    } catch (Exception e) {
      System.out.println("Log failed but payment continues");
    }
  }
}