package io.snankara.github.food.order.domain.event.publisher;

import io.snankara.github.food.order.domain.event.DomainEvent;

public interface DomainEventPublisher<T extends DomainEvent> {

    void publish(T domainEvent);
}
