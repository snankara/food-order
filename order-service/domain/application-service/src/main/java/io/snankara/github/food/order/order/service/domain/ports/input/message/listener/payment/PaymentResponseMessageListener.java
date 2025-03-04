package io.snankara.github.food.order.order.service.domain.ports.input.message.listener.payment;

import io.snankara.github.food.order.order.service.domain.dto.message.PaymentResponse;

public interface PaymentResponseMessageListener {

    void paymentCompleted(PaymentResponse paymentResponse);

    void paymentCancelled(PaymentResponse paymentResponse);
}
