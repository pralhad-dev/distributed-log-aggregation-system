package com.logplatform.log.controller;

import com.logplatform.common.dto.GlobalApiResponse;
import com.logplatform.common.dto.LogRequest;
import com.logplatform.common.dto.LogResponse;
import com.logplatform.common.exceptions.DataNotFoundException;
import com.logplatform.log.service.LogService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/logs")
@RequiredArgsConstructor
public class LogController {

  private final LogService logService;

  @PostMapping
  public ResponseEntity<GlobalApiResponse<LogRequest>> createLog(@RequestBody LogRequest logRequest) throws DataNotFoundException {

    return new ResponseEntity<>(logService.createLogs(logRequest), HttpStatus.CREATED);
  }

  @GetMapping
  public ResponseEntity<GlobalApiResponse<List<LogResponse>>> getLogs() {
    return new ResponseEntity<>(logService.findAllLogs(), HttpStatus.OK);
  }

  @GetMapping("/service-name/{serviceName}")
  public ResponseEntity<GlobalApiResponse<List<LogResponse>>> getLogsByService(@PathVariable String serviceName) {
    return new ResponseEntity<>(logService.findByService(serviceName), HttpStatus.OK);
  }

  @GetMapping("{level}")
  public ResponseEntity<GlobalApiResponse<List<LogResponse>>> getLogsByLevel(@PathVariable String level) {
    return new ResponseEntity<>(logService.findByLevel(level),HttpStatus.OK);
  }

}
