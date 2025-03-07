package io.snankara.github.food.order.order.service.domain;

import io.snankara.github.food.order.order.service.domain.event.OrderCreatedEvent;
import io.snankara.github.food.order.order.service.domain.ports.output.message.publisher.payment.OrderCreatedPaymentRequestMessagePublisher;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.transaction.event.TransactionalEventListener;

@Slf4j
@Component
public class OrderCreatedEventApplicationListener {

    private final OrderCreatedPaymentRequestMessagePublisher orderCreatedPaymentRequestMessagePublisher;

    public OrderCreatedEventApplicationListener(OrderCreatedPaymentRequestMessagePublisher orderCreatedPaymentRequestMessagePublisher) {
        this.orderCreatedPaymentRequestMessagePublisher = orderCreatedPaymentRequestMessagePublisher;
    }

    @TransactionalEventListener
    void process(OrderCreatedEvent orderCreatedEvent) {
        orderCreatedPaymentRequestMessagePublisher.publish(orderCreatedEvent);
    }
}
