package io.snankara.github.food.order.order.service.domain;

import io.snankara.github.food.order.order.service.domain.dto.track.TrackOrderQuery;
import io.snankara.github.food.order.order.service.domain.dto.track.TrackOrderResponse;
import io.snankara.github.food.order.order.service.domain.entity.Order;
import io.snankara.github.food.order.order.service.domain.exception.OrderNotFoundException;
import io.snankara.github.food.order.order.service.domain.mapper.OrderDataMapper;
import io.snankara.github.food.order.order.service.domain.ports.output.repository.OrderRepository;
import io.snankara.github.food.order.order.service.domain.valueobject.TrackingId;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Component
@Slf4j
public class OrderTrackCommandHandler {

    private final OrderDataMapper orderDataMapper;
    private final OrderRepository orderRepository;

    public OrderTrackCommandHandler(OrderDataMapper orderDataMapper, OrderRepository orderRepository) {
        this.orderDataMapper = orderDataMapper;
        this.orderRepository = orderRepository;
    }

    @Transactional(readOnly = true)
    public TrackOrderResponse track(TrackOrderQuery trackOrderQuery){
        Optional<Order> trackedOrder = orderRepository.findByTrackingId(new TrackingId(trackOrderQuery.getOrderTrackingId()));

        if (trackedOrder.isEmpty()){
            log.warn("Order tracking id {} not found", trackOrderQuery.getOrderTrackingId());
            throw new OrderNotFoundException("Order not found with tracking id " + trackOrderQuery.getOrderTrackingId());
        }

        return orderDataMapper.orderToTrackOrderResponse(trackedOrder.get());
    }
}
