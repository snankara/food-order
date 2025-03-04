package io.snankara.github.food.order.order.service.domain.ports.output.message.publisher.restaurantapproval;

import io.snankara.github.food.order.domain.event.publisher.DomainEventPublisher;
import io.snankara.github.food.order.order.service.domain.event.OrderPaidEvent;

public interface OrderPaidRestaurantRequestMessagePublisher extends DomainEventPublisher<OrderPaidEvent> {
}
