package com.logplatform.log.repository;

import com.logplatform.log.entity.LogEvent;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface LogRepository extends JpaRepository<LogEvent, Long> {

  // filter by service name
  List<LogEvent> findByServiceName(String serviceName);

  // filter by log level (INFO, ERROR, WARN)
  List<LogEvent> findByLevel(String level);

  List<LogEvent> findByServiceNameAndLevel(String serviceName, String level);
}