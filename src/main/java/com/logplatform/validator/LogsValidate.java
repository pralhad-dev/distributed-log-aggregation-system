package com.logplatform.validator;

import com.logplatform.common.dto.LogRequest;
import org.springframework.stereotype.Component;

@Component
public class LogsValidate {
  public void validateLogs(LogRequest logRequest) {
    if (logRequest == null) {
      throw new RuntimeException("LogRequest is null");
    }
  }
}
