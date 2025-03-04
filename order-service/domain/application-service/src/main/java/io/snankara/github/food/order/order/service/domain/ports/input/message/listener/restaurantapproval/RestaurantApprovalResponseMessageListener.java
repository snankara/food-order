package io.snankara.github.food.order.order.service.domain.ports.input.message.listener.restaurantapproval;

import io.snankara.github.food.order.order.service.domain.dto.message.RestaurantApprovalResponse;

public interface RestaurantApprovalResponseMessageListener {

    void orderApproved(RestaurantApprovalResponse restaurantApprovalResponse);

    void orderRejected(RestaurantApprovalResponse restaurantApprovalResponse);
}
