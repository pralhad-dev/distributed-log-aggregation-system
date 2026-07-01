package com.logplatform.log.service.impl;

import com.logplatform.common.Mapper.LogMapper;
import com.logplatform.common.dto.GlobalApiResponse;
import com.logplatform.common.dto.LogRequest;
import com.logplatform.common.dto.LogResponse;
import com.logplatform.common.enums.ResponseStatus;
import com.logplatform.common.exceptions.DataNotFoundException;
import com.logplatform.log.entity.LogEvent;
import com.logplatform.log.repository.LogRepository;
import com.logplatform.log.service.LogService;
import com.logplatform.validator.LogsValidate;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Slf4j
@Service
public class LogSerciveImpl implements LogService {

  private final LogRepository logRepository;
  private final LogsValidate logsValidate;
  private final LogMapper logMapper;

  public LogSerciveImpl(LogRepository logRepository, LogsValidate logsValidate, LogMapper logMapper) {
    this.logRepository = logRepository;
    this.logsValidate = logsValidate;
    this.logMapper = logMapper;
  }

  @Override
  public GlobalApiResponse<LogRequest> createLogs(LogRequest logRequest) throws DataNotFoundException {
    GlobalApiResponse<LogRequest> response = new GlobalApiResponse<>();
    logsValidate.validateLogs(logRequest);

    LogEvent logEvent = new LogEvent();
    logEvent.setLevel(logRequest.level());
    logEvent.setMessage(logRequest.message());
    logEvent.setServiceName(logRequest.serviceName());
    logEvent.setCreatedAt(LocalDateTime.now());
    logEvent.setTraceId(logRequest.traceId());

    logRepository.save(logEvent);

    LogRequest dto = logMapper.convertToDto(logEvent);

    response.setResponseCode(String.valueOf(HttpStatus.CREATED));
    response.setResponseMsg("Logs Saved Successfully");
    response.setResponseData(dto);
    response.setStatus(ResponseStatus.SUCCESS);

    return response;
  }

  @Override
  public GlobalApiResponse<List<LogResponse>> findAllLogs() {
    GlobalApiResponse<List<LogResponse>> response = new GlobalApiResponse<>();

    List<LogEvent> logs = logRepository.findAll();

    List<LogResponse> toDto = logMapper.mapToDto(logs);

    response.setResponseCode(String.valueOf(HttpStatus.OK));
    response.setResponseMsg("Logs fetched Successfully");
    response.setResponseData(toDto);
    response.setStatus(ResponseStatus.SUCCESS);
    return response;
  }

  @Override
  public GlobalApiResponse<List<LogResponse>> findByService(String serviceName) {
    GlobalApiResponse<List<LogResponse>> response = new GlobalApiResponse<>();
    if (serviceName == null || serviceName.isEmpty()) {
      throw new IllegalArgumentException("Service name cannot be empty");
    }

    List<LogEvent> logEvents = logRepository.findByServiceName(serviceName);
    List<LogResponse> toDto = logMapper.mapToDto(logEvents);

    response.setResponseCode(String.valueOf(HttpStatus.OK));
    response.setResponseMsg("Logs fetched Successfully");
    response.setResponseData(toDto);
    response.setStatus(ResponseStatus.SUCCESS);
    return response;
  }

  @Override
  public GlobalApiResponse<List<LogResponse>> findByLevel(String level) {
    GlobalApiResponse<List<LogResponse>> response = new GlobalApiResponse<>();

    if (level == null || level.isEmpty()) {
      throw new IllegalArgumentException("level name cannot be empty");
    }

    List<LogEvent> logEvents = logRepository.findByLevel(level);
    List<LogResponse> toDto = logMapper.mapToDto(logEvents);

    response.setResponseCode(String.valueOf(HttpStatus.OK));
    response.setResponseMsg("Logs fetched Successfully");
    response.setResponseData(toDto);
    response.setStatus(ResponseStatus.SUCCESS);
    return response;
  }

}
