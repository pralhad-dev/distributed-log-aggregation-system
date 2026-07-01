package com.logplatform.modules.order.service;

import com.logplatform.common.client.LogClient;
import com.logplatform.modules.order.dto.OrderRequest;
import com.logplatform.modules.order.dto.OrderResponse;
import com.logplatform.modules.order.entity.Order;
import com.logplatform.modules.order.repository.OrderRepository;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.UUID;

@Service
public class OrderService {

  private final OrderRepository orderRepository;
  private final LogClient logClient;

  public OrderService(OrderRepository orderRepository, LogClient logClient) {
    this.orderRepository = orderRepository;
    this.logClient = logClient;
  }

  public OrderResponse createOrder(OrderRequest request) {

    String traceId = UUID.randomUUID().toString();

    logClient.sendLog("order-service", "INFO", "Order request received");

    try {

      if (request.quantity() <= 0) {
        logClient.sendLog("order-service", "ERROR", "Invalid quantity");
        return new OrderResponse("FAILED", "Invalid quantity");
      }

      Order order = new Order();
      order.setUserId(request.userId());
      order.setProductName(request.productName());
      order.setQuantity(request.quantity());
      order.setStatus("PLACED");
      order.setCreatedAt(LocalDateTime.now());

      orderRepository.save(order);

      logClient.sendLog("order-service", "INFO", "Order placed successfully");

      return new OrderResponse("SUCCESS", "Order created");

    } catch (Exception e) {

      logClient.sendLog("order-service", "ERROR", e.getMessage());

      return new OrderResponse("ERROR", "Something went wrong");
    }
  }
}