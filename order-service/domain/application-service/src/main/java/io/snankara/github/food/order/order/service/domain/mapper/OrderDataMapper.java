package io.snankara.github.food.order.order.service.domain.mapper;

import io.snankara.github.food.order.domain.valueobject.CustomerId;
import io.snankara.github.food.order.domain.valueobject.Money;
import io.snankara.github.food.order.domain.valueobject.ProductId;
import io.snankara.github.food.order.domain.valueobject.RestaurantId;
import io.snankara.github.food.order.order.service.domain.dto.create.CreateOrderCommand;
import io.snankara.github.food.order.order.service.domain.dto.create.CreateOrderResponse;
import io.snankara.github.food.order.order.service.domain.dto.create.OrderAddress;
import io.snankara.github.food.order.order.service.domain.entity.Order;
import io.snankara.github.food.order.order.service.domain.entity.OrderItem;
import io.snankara.github.food.order.order.service.domain.entity.Product;
import io.snankara.github.food.order.order.service.domain.entity.Restaurant;
import io.snankara.github.food.order.order.service.domain.valueobject.StreetAddress;
import io.snankara.github.food.order.order.service.domain.valueobject.TrackingId;
import org.springframework.stereotype.Component;

import javax.validation.constraints.NotNull;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Component
public class OrderDataMapper {

    public Restaurant createOrderCommandToRestaurant(CreateOrderCommand createOrderCommand){
        return Restaurant.builder()
                .id(new RestaurantId(createOrderCommand.getRestaurantId()))
                .products(createOrderCommand.getItems().stream().map(orderItem ->
                        new Product(new ProductId(orderItem.getProductId())))
                        .collect(Collectors.toList()))
                .build();
    }

    public Order createOrderCommandToOrder(CreateOrderCommand createOrderCommand){
        return Order.builder()
                .customerId(new CustomerId(createOrderCommand.getCustomerId()))
                .restaurantId(new RestaurantId(createOrderCommand.getRestaurantId()))
                .deliveryAddress(orderAddressToStreetAddress(createOrderCommand.getAddress()))
                .price(new Money(createOrderCommand.getPrice()))
                .items(orderItemsToOrderItemEntities(createOrderCommand.getItems()))
                .build();
    }

    public CreateOrderResponse orderToCreateOrderResponse(Order order){
        return CreateOrderResponse.builder()
                .orderTrackingId(order.getTrackingId().getValue())
                .orderStatus(order.getOrderStatus())
                .message("Order created successfully.")
                .build();
    }

    private List<OrderItem> orderItemsToOrderItemEntities(List<io.snankara.github.food.order.order.service.domain.dto.create.OrderItem> orderItems) {
        return orderItems.stream()
                .map(orderItem ->
                        OrderItem.builder()
                                .product(new Product(new ProductId(orderItem.getProductId())))
                                .price(new Money(orderItem.getPrice()))
                                .quantity(orderItem.getQuantity())
                                .subTotal(new Money(orderItem.getSubTotal()))
                                .build()).collect(Collectors.toList());
    }

    private StreetAddress orderAddressToStreetAddress(OrderAddress orderAddress) {
        return new StreetAddress(
                UUID.randomUUID(),
                orderAddress.getStreet(),
                orderAddress.getPostalCode(),
                orderAddress.getCity()
        );
    }

}

