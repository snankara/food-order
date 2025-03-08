package io.snankara.github.food.order.order.service.domain;

import io.snankara.github.food.order.domain.valueobject.*;
import io.snankara.github.food.order.order.service.domain.dto.create.CreateOrderCommand;
import io.snankara.github.food.order.order.service.domain.dto.create.CreateOrderResponse;
import io.snankara.github.food.order.order.service.domain.dto.create.OrderAddress;
import io.snankara.github.food.order.order.service.domain.dto.create.OrderItem;
import io.snankara.github.food.order.order.service.domain.entity.Customer;
import io.snankara.github.food.order.order.service.domain.entity.Order;
import io.snankara.github.food.order.order.service.domain.entity.Product;
import io.snankara.github.food.order.order.service.domain.entity.Restaurant;
import io.snankara.github.food.order.order.service.domain.exception.OrderDomainException;
import io.snankara.github.food.order.order.service.domain.mapper.OrderDataMapper;
import io.snankara.github.food.order.order.service.domain.ports.input.service.OrderApplicationService;
import io.snankara.github.food.order.order.service.domain.ports.output.repository.CustomerRepository;
import io.snankara.github.food.order.order.service.domain.ports.output.repository.OrderRepository;
import io.snankara.github.food.order.order.service.domain.ports.output.repository.RestaurantRepository;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
@SpringBootTest(classes = OrderTestConfiguration.class)
public class OrderApplicationServiceTest {

    @Autowired
    private OrderApplicationService orderApplicationService;

    @Autowired
    private OrderDataMapper orderDataMapper;

    @Autowired
    private OrderRepository orderRepository;

    @Autowired
    private RestaurantRepository restaurantRepository;

    @Autowired
    private CustomerRepository customerRepository;

    private CreateOrderCommand createOrderCommand;
    private CreateOrderCommand createOrderCommandWrongTotalPrice;
    private CreateOrderCommand createOrderCommandWrongProductPrice;

    private final UUID CUSTOMER_ID = UUID.fromString("f1b9b1b1-0b1b-4b1b-8b1b-2b1b1b1b1b1b");
    private final UUID ORDER_ID = UUID.fromString("f1b9b1b1-0b1b-4b1b-8b1b-2b1b1b1b1b1b");
    private final UUID RESTAURANT_ID = UUID.fromString("f1b9b1b1-0b1b-4b1b-8b1b-2b1b1b1b1b1b");
    private final UUID PRODUCT_ID = UUID.fromString("f1b9b1b1-0b1b-4b1b-8b1b-2b1b1b1b1b1b");
    private final BigDecimal PRICE = new BigDecimal("200.00");

    @BeforeAll
    public void init(){
        createOrderCommand = CreateOrderCommand.builder()
                .customerId(CUSTOMER_ID)
                .restaurantId(RESTAURANT_ID)
                .address(OrderAddress.builder()
                        .street("street_1")
                        .postalCode("postal_code_1")
                        .city("city_1")
                        .build())
                .price(PRICE)
                .items(List.of(OrderItem.builder()
                                .productId(PRODUCT_ID)
                                .quantity(1)
                                .price(new BigDecimal("50.00"))
                                .subTotal(new BigDecimal("50.00"))
                                .build(),
                        OrderItem.builder()
                                .productId(PRODUCT_ID)
                                .quantity(3)
                                .price(new BigDecimal("50.00"))
                                .subTotal(new BigDecimal("150.00"))
                                .build()))
                .build();

        createOrderCommandWrongTotalPrice = CreateOrderCommand.builder()
                .customerId(CUSTOMER_ID)
                .restaurantId(RESTAURANT_ID)
                .address(OrderAddress.builder()
                        .street("street_1")
                        .postalCode("postal_code_1")
                        .city("city_1")
                        .build())
                .price(new BigDecimal("250.00"))
                .items(List.of(OrderItem.builder()
                                .productId(PRODUCT_ID)
                                .quantity(1)
                                .price(new BigDecimal("50.00"))
                                .subTotal(new BigDecimal("50.00"))
                                .build(),
                        OrderItem.builder()
                                .productId(PRODUCT_ID)
                                .quantity(3)
                                .price(new BigDecimal("50.00"))
                                .subTotal(new BigDecimal("150.00"))
                                .build()))
                .build();

        createOrderCommandWrongProductPrice = CreateOrderCommand.builder()
                .customerId(CUSTOMER_ID)
                .restaurantId(RESTAURANT_ID)
                .address(OrderAddress.builder()
                        .street("street_1")
                        .postalCode("postal_code_1")
                        .city("city_1")
                        .build())
                .price(new BigDecimal("210.00"))
                .items(List.of(OrderItem.builder()
                                .productId(PRODUCT_ID)
                                .quantity(1)
                                .price(new BigDecimal("60.00"))
                                .subTotal(new BigDecimal("60.00"))
                                .build(),
                        OrderItem.builder()
                                .productId(PRODUCT_ID)
                                .quantity(3)
                                .price(new BigDecimal("50.00"))
                                .subTotal(new BigDecimal("150.00"))
                                .build()))
                .build();

        Customer customer = new Customer();
        customer.setId(new CustomerId(CUSTOMER_ID));

        Restaurant restaurantResponse = Restaurant.builder()
                .id(new RestaurantId(createOrderCommand.getRestaurantId()))
                .products(List.of(
                        new Product(new ProductId(PRODUCT_ID), new Money(new BigDecimal("50.00")), "product_1"),
                        new Product(new ProductId(PRODUCT_ID), new Money(new BigDecimal("50.00")), "product_2")))
                .active(true)
                .build();

        Order order = orderDataMapper.createOrderCommandToOrder(createOrderCommand);
        order.setId(new OrderId(ORDER_ID));

        when(customerRepository.findCustomer(CUSTOMER_ID)).thenReturn(Optional.of(customer));
        when(restaurantRepository.findRestaurantInformation(orderDataMapper.createOrderCommandToRestaurant(createOrderCommand)))
                .thenReturn(Optional.of(restaurantResponse));
        when(orderRepository.save(any(Order.class))).thenReturn(order);
    }

    @Test
    public void tesCreateOrder(){
        CreateOrderResponse createOrderResponse = orderApplicationService.create(createOrderCommand);
        assertEquals(OrderStatus.PENDING, createOrderResponse.getOrderStatus());
        assertEquals("Order created successfully.", createOrderResponse.getMessage());
        assertNotNull(createOrderResponse.getOrderTrackingId());
    }

    @Test
    public void testCreateOrderWithWrongTotalPrice(){
        OrderDomainException orderDomainException = assertThrows(OrderDomainException.class, () ->
                orderApplicationService.create(createOrderCommandWrongTotalPrice));
        assertEquals("Total price: 250.00 is not equal to Order items total: 200.00", orderDomainException.getMessage());
    }

    @Test
    public void testCreateOrderWithWrongProductPrice(){
        OrderDomainException orderDomainException = assertThrows(OrderDomainException.class, () ->
                orderApplicationService.create(createOrderCommandWrongProductPrice));
        assertEquals("Order item price: 60.00 is not valid for product "
                + PRODUCT_ID ,orderDomainException.getMessage());
    }

    @Test
    public void testCreateOrderWithPassiveRestaurant(){
        Restaurant restaurantResponse = Restaurant.builder()
                .id(new RestaurantId(createOrderCommand.getRestaurantId()))
                .products(List.of(
                        new Product(new ProductId(PRODUCT_ID), new Money(new BigDecimal("50.00")), "product_1"),
                        new Product(new ProductId(PRODUCT_ID), new Money(new BigDecimal("50.00")), "product_2")))
                .active(false)
                .build();

        when(restaurantRepository.findRestaurantInformation(orderDataMapper.createOrderCommandToRestaurant(createOrderCommand)))
                .thenReturn(Optional.of(restaurantResponse));
        OrderDomainException orderDomainException = assertThrows(OrderDomainException.class, () ->
                orderApplicationService.create(createOrderCommand));
        assertEquals("Restaurant with id " + restaurantResponse.getId().getValue()
                + " is currently not active." ,orderDomainException.getMessage());
    }
}
