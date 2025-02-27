package io.snankara.github.food.order.order.service.domain.event;

import io.snankara.github.food.order.order.service.domain.entity.Order;

import java.time.ZonedDateTime;

public class OrderPaidEvent extends OrderEvent {

    public OrderPaidEvent(Order order, ZonedDateTime craetedAt) {
        super(order, craetedAt);
    }
}
