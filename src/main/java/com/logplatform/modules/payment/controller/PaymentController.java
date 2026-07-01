package com.logplatform.modules.payment.controller;

import com.logplatform.modules.payment.dto.PaymentRequest;
import com.logplatform.modules.payment.dto.PaymentResponse;
import com.logplatform.modules.payment.service.PaymentService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/payment")
public class PaymentController {

  private final PaymentService paymentService;

  public PaymentController(PaymentService paymentService) {
    this.paymentService = paymentService;
  }

  @PostMapping
  public ResponseEntity<PaymentResponse> pay(@RequestBody PaymentRequest request) {
    return ResponseEntity.ok(paymentService.processPayment(request));
  }
}