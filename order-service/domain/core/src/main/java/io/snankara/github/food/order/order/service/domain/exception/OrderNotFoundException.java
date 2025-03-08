package io.snankara.github.food.order.order.service.domain.exception;

import io.snankara.github.food.order.domain.exception.DomainException;

public class OrderNotFoundException extends DomainException {

  public OrderNotFoundException(String message) {
    super(message);
  }

  public OrderNotFoundException(String message, Throwable cause) {
    super(message, cause);
  }
}
