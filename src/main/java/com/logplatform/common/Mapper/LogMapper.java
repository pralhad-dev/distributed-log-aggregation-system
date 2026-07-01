package com.logplatform.common.Mapper;

import com.logplatform.common.dto.LogRequest;
import com.logplatform.common.dto.LogResponse;
import com.logplatform.log.entity.LogEvent;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.util.List;

@Slf4j
@Component
public class LogMapper {
  public LogRequest convertToDto(LogEvent logEvent) {

    return new LogRequest(
        logEvent.getId(),
        logEvent.getServiceName(),
        logEvent.getLevel(),
        logEvent.getMessage(),
        logEvent.getTraceId()
    );
  }

  public List<LogResponse> mapToDto(List<LogEvent> logEvents) {

    return logEvents.stream()
        .map(log -> new LogResponse(
            log.getId(),
            log.getServiceName(),
            log.getLevel(),
            log.getMessage(),
            log.getTraceId(),
            log.getCreatedAt()
        ))
        .toList();
  }
}
