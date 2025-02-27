package io.snankara.github.food.order.order.service.domain;

import io.snankara.github.food.order.order.service.domain.entity.Order;
import io.snankara.github.food.order.order.service.domain.entity.Restaurant;
import io.snankara.github.food.order.order.service.domain.event.OrderCancelledEvent;
import io.snankara.github.food.order.order.service.domain.event.OrderCreatedEvent;
import io.snankara.github.food.order.order.service.domain.event.OrderPaidEvent;

import java.util.List;

public interface OrderDomainService {

    OrderCreatedEvent validateAndInitiate(Order order, Restaurant restaurant);

    OrderPaidEvent pay(Order order);

    void approve(Order order);

    OrderCancelledEvent cancelPayment(Order order, List<String> failureMessages);

    void cancelOrder(Order order, List<String> failureMessages);
}
