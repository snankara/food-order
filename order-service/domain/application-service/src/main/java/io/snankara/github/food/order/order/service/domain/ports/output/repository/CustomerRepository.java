package io.snankara.github.food.order.order.service.domain.ports.output.repository;

import io.snankara.github.food.order.order.service.domain.entity.Customer;

import java.util.Optional;
import java.util.UUID;

public interface CustomerRepository {

    Optional<Customer> findCustomer(UUID customerId);
}
