package com.logplatform.modules.payment.service;

import com.logplatform.common.client.LogClient;
import com.logplatform.modules.payment.dto.PaymentRequest;
import com.logplatform.modules.payment.dto.PaymentResponse;
import com.logplatform.modules.payment.entity.Payment;
import com.logplatform.modules.payment.repository.PaymentRepository;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.UUID;

@Service
public class PaymentService {

  private final PaymentRepository paymentRepository;
  private final LogClient logClient;

  public PaymentService(PaymentRepository paymentRepository, LogClient logClient) {
    this.paymentRepository = paymentRepository;
    this.logClient = logClient;
  }

  public PaymentResponse processPayment(PaymentRequest request) {

    String traceId = UUID.randomUUID().toString();

    logClient.sendLog("payment-service", "INFO", "Payment request received");


    Payment payment = new Payment();
    payment.setUserId(request.userId());
    payment.setAmount(request.amount());
    payment.setCreatedAt(LocalDateTime.now());

    if (request.amount().doubleValue() <= 0) {
      payment.setStatus("FAILED");
      paymentRepository.save(payment);

      logClient.sendLog("payment-service", "ERROR", "Invalid payment amount");

      return new PaymentResponse("FAILED", "Invalid amount");
    }

    payment.setStatus("SUCCESS");
    paymentRepository.save(payment);

    logClient.sendLog("payment-service", "INFO", "Payment completed successfully");

    return new PaymentResponse("SUCCESS", "Payment processed");
  }
}