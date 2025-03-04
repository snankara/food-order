package io.snankara.github.food.order.order.service.domain.ports.input.service;

import io.snankara.github.food.order.order.service.domain.dto.create.CreateOrderCommand;
import io.snankara.github.food.order.order.service.domain.dto.create.CreateOrderResponse;
import io.snankara.github.food.order.order.service.domain.dto.track.TrackOrderQuery;
import io.snankara.github.food.order.order.service.domain.dto.track.TrackOrderResponse;

import javax.validation.Valid;

public interface OrderApplicationService {

    CreateOrderResponse create(@Valid CreateOrderCommand createOrderCommand);

    TrackOrderResponse track(@Valid TrackOrderQuery trackOrderQuery);
}
