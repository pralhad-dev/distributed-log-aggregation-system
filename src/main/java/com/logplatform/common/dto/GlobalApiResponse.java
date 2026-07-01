package com.logplatform.common.dto;


import com.fasterxml.jackson.annotation.JsonInclude;
import com.logplatform.common.enums.ResponseStatus;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.time.LocalDateTime;

@Data
@NoArgsConstructor
public class GlobalApiResponse<T> implements Serializable {

  private String responseCode;
  private String responseMsg;
  private ResponseStatus status;
  private String timestamp = String.valueOf(LocalDateTime.now());
  private T responseData;

  @JsonInclude(JsonInclude.Include.NON_NULL)
  private Integer totalRecords;

  public GlobalApiResponse(String responseCode, String responseMsg, ResponseStatus status, String timestamp, T responseData, Integer totalRecords) {
    this.responseCode = responseCode;
    this.responseMsg = responseMsg;
    this.status = status;
    this.timestamp = timestamp;
    this.responseData = responseData;
    this.totalRecords = totalRecords;
  }
}
