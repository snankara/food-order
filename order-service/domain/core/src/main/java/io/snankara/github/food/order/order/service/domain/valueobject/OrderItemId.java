package io.snankara.github.food.order.order.service.domain.valueobject;

import io.snankara.github.food.order.domain.valueobject.BaseId;

public class OrderItemId extends BaseId<Long> {
    public OrderItemId(Long value) {
        super(value);
    }
}
