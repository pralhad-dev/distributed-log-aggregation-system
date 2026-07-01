package com.logplatform.log.entity;

import jakarta.persistence.*;
import lombok.Data;

import java.time.LocalDateTime;

@Entity
@Table(name = "logs")
@Data
public class LogEvent {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  @Column(name = "service_name", nullable = false)
  private String serviceName;

  @Column(name = "level", nullable = false)
  private String level;

  @Column(name = "message", nullable = false, columnDefinition = "TEXT")
  private String message;

  @Column(name = "trace_id", nullable = false)
  private String traceId;

  @Column(name = "created_at")
  private LocalDateTime createdAt;
}