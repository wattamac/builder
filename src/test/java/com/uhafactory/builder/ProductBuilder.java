package com.uhafactory.builder;

import com.google.common.collect.Lists;
import sample.Product;

import java.util.List;

/**
 * Created by lineplus on 2016. 6. 1..
 */
public class ProductBuilder extends AbstractBuilder<Product> {
    @Override
    protected Class getTargetClass() {
        return Product.class;
    }

    private long id;
    private String name = "name";
    private DiscountBuilder discount = DiscountBuilder.aDiscount();

    private List<ImageBuilder> images = Lists.newArrayList();

    public static ProductBuilder aProduct() {
        return new ProductBuilder();
    }

    public ProductBuilder withId(long id) {
        this.id = id;
        return this;
    }

    public ProductBuilder withDiscount(DiscountBuilder discount) {
        this.discount = discount;
        return this;
    }

    public ProductBuilder withImages(ImageBuilder ... images) {
        this.images = Lists.newArrayList(images);
        return this;
    }
}
