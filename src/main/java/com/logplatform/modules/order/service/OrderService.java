package com.logplatform.modules.order.service;

import com.logplatform.common.client.LogClient;
import com.logplatform.modules.order.dto.OrderRequest;
import com.logplatform.modules.order.dto.OrderResponse;
import com.logplatform.modules.order.entity.Order;
import com.logplatform.modules.order.repository.OrderRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Slf4j
@Service
public class OrderService {

  private final OrderRepository orderRepository;
  private final LogClient logClient;

  public OrderService(OrderRepository orderRepository, LogClient logClient) {
    this.orderRepository = orderRepository;
    this.logClient = logClient;
  }

  public OrderResponse createOrder(OrderRequest request) {

    logClient.sendLog("order-service", "INFO", "Order creation started");

    try {

      if (request.quantity() == null || request.quantity() <= 0) {
        logClient.sendLog("order-service", "ERROR", "Invalid order quantity");
        return new OrderResponse("FAILED", "Quantity must be greater than 0");
      }

      Order order = new Order();
      order.setUserId(request.userId());
      order.setProductName(request.productName());
      order.setQuantity(request.quantity());
      order.setStatus("PLACED");
      order.setCreatedAt(LocalDateTime.now());

      Order savedOrder = orderRepository.save(order);

      logClient.sendLog("order-service", "INFO", "Order saved successfully. OrderId=" + savedOrder.getId());
      return new OrderResponse("SUCCESS", "Order created");

    } catch (RuntimeException ex) {

      log.error("Order creation failed", ex);
      logClient.sendLog("order-service", "ERROR", "Order creation failed: " + ex.getMessage());
      return new OrderResponse("ERROR", "Order processing failed");
    }
  }
}