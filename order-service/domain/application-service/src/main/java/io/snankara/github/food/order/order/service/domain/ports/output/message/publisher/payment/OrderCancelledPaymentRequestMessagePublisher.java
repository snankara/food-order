package io.snankara.github.food.order.order.service.domain.ports.output.message.publisher.payment;

import io.snankara.github.food.order.domain.event.publisher.DomainEventPublisher;
import io.snankara.github.food.order.order.service.domain.event.OrderCancelledEvent;

public interface OrderCancelledPaymentRequestMessagePublisher extends DomainEventPublisher<OrderCancelledEvent> {
}
