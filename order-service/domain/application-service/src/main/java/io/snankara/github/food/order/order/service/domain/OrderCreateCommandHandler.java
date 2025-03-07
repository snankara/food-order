package io.snankara.github.food.order.order.service.domain;

import io.snankara.github.food.order.order.service.domain.dto.create.CreateOrderCommand;
import io.snankara.github.food.order.order.service.domain.dto.create.CreateOrderResponse;
import io.snankara.github.food.order.order.service.domain.entity.Customer;
import io.snankara.github.food.order.order.service.domain.entity.Order;
import io.snankara.github.food.order.order.service.domain.entity.Restaurant;
import io.snankara.github.food.order.order.service.domain.event.OrderCreatedEvent;
import io.snankara.github.food.order.order.service.domain.exception.OrderDomainException;
import io.snankara.github.food.order.order.service.domain.mapper.OrderDataMapper;
import io.snankara.github.food.order.order.service.domain.ports.output.message.publisher.payment.OrderCreatedPaymentRequestMessagePublisher;
import io.snankara.github.food.order.order.service.domain.ports.output.repository.CustomerRepository;
import io.snankara.github.food.order.order.service.domain.ports.output.repository.OrderRepository;
import io.snankara.github.food.order.order.service.domain.ports.output.repository.RestaurantRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;
import java.util.UUID;

@Slf4j
@Component
public class OrderCreateCommandHandler {


    private final OrderCreateHelper orderCreateHelper;
    private final OrderDataMapper orderDataMapper;
    private final OrderCreatedPaymentRequestMessagePublisher orderCreatedPaymentRequestMessagePublisher;

    public OrderCreateCommandHandler(OrderCreateHelper orderCreateHelper,
                                     OrderDataMapper orderDataMapper,
                                     OrderCreatedPaymentRequestMessagePublisher orderCreatedPaymentRequestMessagePublisher) {
        this.orderCreateHelper = orderCreateHelper;
        this.orderDataMapper = orderDataMapper;
        this.orderCreatedPaymentRequestMessagePublisher = orderCreatedPaymentRequestMessagePublisher;
    }

    public CreateOrderResponse create(CreateOrderCommand createOrderCommand){
        OrderCreatedEvent orderCreatedEvent = orderCreateHelper.persist(createOrderCommand);
        log.info("Order created with tracking id: {}", orderCreatedEvent.getOrder().getTrackingId().getValue());
        orderCreatedPaymentRequestMessagePublisher.publish(orderCreatedEvent);
        return orderDataMapper.orderToCreateOrderResponse(orderCreatedEvent.getOrder());
    }

}
