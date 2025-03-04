package io.snankara.github.food.order.order.service.domain.ports.output.repository;

import io.snankara.github.food.order.order.service.domain.entity.Restaurant;

import java.util.Optional;

public interface RestaurantRepository {

    Optional<Restaurant> findRestaurantInformation(Restaurant restaurant);
}
