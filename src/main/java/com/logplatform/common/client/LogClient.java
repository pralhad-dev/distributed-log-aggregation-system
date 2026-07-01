package com.logplatform.common.client;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.logplatform.common.context.TraceContext;
import com.logplatform.common.dto.LogRequest;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Component;

@Component
public class LogClient {

  private final KafkaTemplate<String, String> kafkaTemplate;
  private final ObjectMapper objectMapper;

  public LogClient(KafkaTemplate<String, String> kafkaTemplate, ObjectMapper objectMapper) {
    this.kafkaTemplate = kafkaTemplate;
    this.objectMapper = objectMapper;
  }

  public void sendLog(String service, String level, String message) {

    LogRequest request = new LogRequest(
            null,
            service,
            level,
            message,
            TraceContext.getTraceId()
        );

    sendLog(request);
  }

  public void sendLog(LogRequest request) {
    try {

      String payload = objectMapper.writeValueAsString(request);
      kafkaTemplate.send("log-topic", payload);

    } catch (Exception ex) {
      System.out.println("Log publish failed");
    }
  }
}