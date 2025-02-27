package io.snankara.github.food.order.order.service.domain.event;

import io.snankara.github.food.order.domain.event.DomainEvent;
import io.snankara.github.food.order.order.service.domain.entity.Order;

import java.time.ZonedDateTime;

public abstract class OrderEvent implements DomainEvent<Order> {
    private final Order order;
    private final ZonedDateTime craetedAt;

    public OrderEvent(Order order, ZonedDateTime craetedAt) {
        this.order = order;
        this.craetedAt = craetedAt;
    }

    public Order getOrder() {
        return order;
    }

    public ZonedDateTime getCraetedAt() {
        return craetedAt;
    }
}
