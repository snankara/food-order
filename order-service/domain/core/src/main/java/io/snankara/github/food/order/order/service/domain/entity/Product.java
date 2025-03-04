package io.snankara.github.food.order.order.service.domain.entity;

import io.snankara.github.food.order.domain.entity.BaseEntity;
import io.snankara.github.food.order.domain.valueobject.Money;
import io.snankara.github.food.order.domain.valueobject.ProductId;

public class Product extends BaseEntity<ProductId> {
    private String name;
    private Money price;

    public Product(ProductId productId ,Money price, String name) {
        super.setId(productId);
        this.price = price;
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public Money getPrice() {
        return price;
    }

    public void updateWithConfirmedNamedAndProduct(String name, Money price) {
        this.name = name;
        this.price = price;
    }
}
