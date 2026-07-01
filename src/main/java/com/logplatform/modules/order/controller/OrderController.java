package com.logplatform.modules.order.controller;

import com.logplatform.modules.order.dto.OrderRequest;
import com.logplatform.modules.order.dto.OrderResponse;
import com.logplatform.modules.order.service.OrderService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/orders")
public class OrderController {

  private final OrderService orderService;

  public OrderController(OrderService orderService) {
    this.orderService = orderService;
  }

  @PostMapping
  public ResponseEntity<OrderResponse> create(@RequestBody OrderRequest request) {
    return ResponseEntity.ok(orderService.createOrder(request));
  }
}