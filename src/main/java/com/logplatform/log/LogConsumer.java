package com.logplatform.log;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.logplatform.common.dto.LogRequest;
import com.logplatform.log.entity.LogEvent;
import com.logplatform.log.repository.LogRepository;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;

@Component
public class LogConsumer {

  private final LogRepository logRepository;
  private final ObjectMapper objectMapper;

  public LogConsumer(LogRepository logRepository, ObjectMapper objectMapper) {
    this.logRepository = logRepository;
    this.objectMapper = objectMapper;
  }

  @KafkaListener(topics = "log-topic", groupId = "log-group")
  public void consume(String message) {

    try {
      LogRequest request = objectMapper.readValue(message, LogRequest.class);

      LogEvent log = new LogEvent();
      log.setServiceName(request.serviceName());
      log.setLevel(request.level());
      log.setMessage(request.message());
      log.setTraceId(request.traceId());
      log.setCreatedAt(LocalDateTime.now());

     // logRepository.save(log);
      LogEvent saved = logRepository.save(log);

      System.out.println("Log saved with id=" + saved.getId());

    } catch (Exception e) {
      System.out.println("Error consuming log");
    }
  }
}