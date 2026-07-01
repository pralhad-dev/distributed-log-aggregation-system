package com.logplatform.common.utils;

import java.util.UUID;

public class TraceIdGenerator {

  public static String generate() {
    return UUID.randomUUID().toString();
  }
}