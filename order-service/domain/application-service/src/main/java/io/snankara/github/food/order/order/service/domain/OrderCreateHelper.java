package io.snankara.github.food.order.order.service.domain;

import io.snankara.github.food.order.order.service.domain.dto.create.CreateOrderCommand;
import io.snankara.github.food.order.order.service.domain.entity.Customer;
import io.snankara.github.food.order.order.service.domain.entity.Order;
import io.snankara.github.food.order.order.service.domain.entity.Restaurant;
import io.snankara.github.food.order.order.service.domain.event.OrderCreatedEvent;
import io.snankara.github.food.order.order.service.domain.exception.OrderDomainException;
import io.snankara.github.food.order.order.service.domain.mapper.OrderDataMapper;
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
public class OrderCreateHelper {
    private final OrderDomainService orderDomainService;
    private final OrderRepository orderRepository;
    private final CustomerRepository customerRepository;
    private final RestaurantRepository restaurantRepository;
    private final OrderDataMapper orderDataMapper;

    public OrderCreateHelper(OrderDomainService orderDomainService,
                             OrderRepository orderRepository,
                             CustomerRepository customerRepository,
                             RestaurantRepository restaurantRepository,
                             OrderDataMapper orderDataMapper) {
        this.orderDomainService = orderDomainService;
        this.orderRepository = orderRepository;
        this.customerRepository = customerRepository;
        this.restaurantRepository = restaurantRepository;
        this.orderDataMapper = orderDataMapper;
    }

    @Transactional
    public OrderCreatedEvent persist(CreateOrderCommand createOrderCommand) {
        checkCustomer(createOrderCommand.getCustomerId());
        Restaurant restaurant = checkRestaurant(createOrderCommand);
        Order order = orderDataMapper.createOrderCommandToOrder(createOrderCommand);
        OrderCreatedEvent orderCreatedEvent = orderDomainService.validateAndInitiate(order, restaurant);
        save(order);
        log.info("Order created with tracking id: {}", order.getTrackingId().getValue());
        return orderCreatedEvent;
    }

    private Restaurant checkRestaurant(CreateOrderCommand createOrderCommand) {
        Restaurant restaurant = orderDataMapper.createOrderCommandToRestaurant(createOrderCommand);
        Optional<Restaurant> optionalRestaurant = restaurantRepository.findRestaurantInformation(restaurant);

        if (optionalRestaurant.isEmpty()){
            log.warn("Could not find restaurant with id: {}", createOrderCommand.getRestaurantId());
            throw new OrderDomainException("Could not find restaurant with id: " + createOrderCommand.getRestaurantId());
        }

        return optionalRestaurant.get();
    }

    private void checkCustomer(UUID customerId) {
        Optional<Customer> customer = customerRepository.findCustomer(customerId);
        if (customer.isEmpty()) {
            log.warn("Could not find customer with id: {}", customerId);
            throw new OrderDomainException("Could not find customer with id: " + customerId);
        }
    }

    private Order save(Order order) {
        Order savedOrder = orderRepository.save(order);
        if (savedOrder == null) {
            log.error("Could not save order");
            throw new OrderDomainException("Could not save order: " + order);
        }

        log.info("Order is saved with id: {}", savedOrder.getId().getValue());
        return savedOrder;
    }
}
