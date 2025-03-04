package io.snankara.github.food.order.order.service.domain.ports.output.repository;

import io.snankara.github.food.order.order.service.domain.entity.Order;
import io.snankara.github.food.order.order.service.domain.valueobject.TrackingId;

import java.util.Optional;

public interface OrderRepository {

    Order save(Order order);

    Optional<Order> findByTrackingId(TrackingId trackingId);
}
