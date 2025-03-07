package io.snankara.github.food.order.order.service.domain;

import io.snankara.github.food.order.domain.event.publisher.DomainEventPublisher;
import io.snankara.github.food.order.order.service.domain.event.OrderCreatedEvent;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.context.ApplicationEventPublisherAware;
import org.springframework.stereotype.Component;

@Slf4j
@Component
public class ApplicationDomainEventPublisher implements ApplicationEventPublisherAware, DomainEventPublisher<OrderCreatedEvent> {

    private ApplicationEventPublisher applicationEventPublisher;

    @Override
    public void setApplicationEventPublisher(ApplicationEventPublisher applicationEventPublisher) {
        this.applicationEventPublisher = applicationEventPublisher;
    }

    @Override
    public void publish(OrderCreatedEvent orderCreatedEvent) {
        this.applicationEventPublisher.publishEvent(orderCreatedEvent);
        log.info("OrderCreatedEvent is published for order id: {}", orderCreatedEvent.getOrder().getId().getValue());
    }
}
