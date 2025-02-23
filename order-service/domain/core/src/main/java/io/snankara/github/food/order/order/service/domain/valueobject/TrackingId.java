package io.snankara.github.food.order.order.service.domain.valueobject;

import io.snankara.github.food.order.domain.valueobject.BaseId;

import java.util.UUID;

public class TrackingId extends BaseId<UUID> {
    public TrackingId(UUID value) {
        super(value);
    }
}
