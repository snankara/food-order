package io.snankara.github.food.order.order.service.domain.event;

import io.snankara.github.food.order.order.service.domain.entity.Order;

import java.time.ZonedDateTime;

public class OrderCreatedEvent extends OrderEvent {

    public OrderCreatedEvent(Order order, ZonedDateTime craetedAt) {
        super(order, craetedAt);
    }
}
