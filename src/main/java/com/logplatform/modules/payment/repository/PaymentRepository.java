package com.logplatform.modules.payment.repository;

import com.logplatform.modules.payment.entity.Payment;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PaymentRepository extends JpaRepository<Payment, Long> {
}