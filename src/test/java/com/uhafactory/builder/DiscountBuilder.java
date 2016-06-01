package com.uhafactory.builder;

import sample.Discount;

import java.math.BigDecimal;

/**
 * Created by lineplus on 2016. 6. 1..
 */
public class DiscountBuilder extends AbstractBuilder<Discount>{
    @Override
    protected Class getTargetClass() {
        return Discount.class;
    }

    private BigDecimal amount;

    public static DiscountBuilder aDiscount() {
        return new DiscountBuilder();
    }

    public DiscountBuilder withAmount(BigDecimal amount){
        this.amount = amount;
        return this;
    }
}
