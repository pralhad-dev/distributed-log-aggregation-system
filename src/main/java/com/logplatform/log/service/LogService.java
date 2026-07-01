package com.logplatform.log.service;

import com.logplatform.common.dto.GlobalApiResponse;
import com.logplatform.common.dto.LogRequest;
import com.logplatform.common.dto.LogResponse;
import com.logplatform.common.exceptions.DataNotFoundException;

import java.util.List;

public interface LogService {
  GlobalApiResponse<LogRequest> createLogs(LogRequest logRequest) throws DataNotFoundException;

  GlobalApiResponse<List<LogResponse>> findAllLogs();

  GlobalApiResponse<List<LogResponse>> findByService(String serviceName);

  GlobalApiResponse<List<LogResponse>> findByLevel(String level);
}
