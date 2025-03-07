package io.snankara.github.food.order.order.service.domain;

import io.snankara.github.food.order.order.service.domain.dto.create.CreateOrderCommand;
import io.snankara.github.food.order.order.service.domain.dto.create.CreateOrderResponse;
import io.snankara.github.food.order.order.service.domain.dto.track.TrackOrderQuery;
import io.snankara.github.food.order.order.service.domain.dto.track.TrackOrderResponse;
import io.snankara.github.food.order.order.service.domain.ports.input.service.OrderApplicationService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.validation.annotation.Validated;

@Slf4j
@Validated
@Service
class OrderApplicationServiceImpl implements OrderApplicationService {

    private final OrderCreateCommandHandler orderCreateCommandHandler;
    private final OrderTrackCommandHandler orderTrackCommandHandler;

    public OrderApplicationServiceImpl(OrderCreateCommandHandler orderCreateCommandHandler, OrderTrackCommandHandler orderTrackCommandHandler) {
        this.orderCreateCommandHandler = orderCreateCommandHandler;
        this.orderTrackCommandHandler = orderTrackCommandHandler;
    }

    @Override
    public CreateOrderResponse create(CreateOrderCommand createOrderCommand) {
        return orderCreateCommandHandler.create(createOrderCommand);
    }

    @Override
    public TrackOrderResponse track(TrackOrderQuery trackOrderQuery) {
        return orderTrackCommandHandler.track(trackOrderQuery);
    }
}
