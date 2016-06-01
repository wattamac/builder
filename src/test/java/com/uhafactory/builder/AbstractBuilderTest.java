package com.uhafactory.builder;


import org.junit.Test;
import sample.Product;

import java.lang.reflect.Field;

import static org.hamcrest.core.Is.is;
import static org.junit.Assert.assertThat;

/**
 * Created by lineplus on 2016. 6. 1..
 */
public class AbstractBuilderTest {

    @Test
    public void testBuild() throws Exception {
        ProductBuilder builder = ProductBuilder.aProduct();
        builder.withId(1L);
        Product productOrder = builder.build();

        assertThat(productOrder.getId(), is(1L));
    }

    @Test
    public void testList() throws Exception {
        ProductBuilder productBuilder = ProductBuilder.aProduct();
        productBuilder.withImages(ImageBuilder.anImage().withUrl("url"));

        Product product = productBuilder.build();

        assertThat(product.getImages().size(), is(1));
        assertThat(product.getImages().get(0).getUrl(), is("url"));
    }

    @Test
    public void testIsPrimitiveObjectType() throws NoSuchFieldException {

        Field field = ProductBuilder.class.getDeclaredField("name");
        assertThat(AbstractBuilder.SupportClassType.PRIMITIVE_OBJECT.isSupportType(field), is(true));

        field = ProductBuilder.class.getDeclaredField("images");
        assertThat(AbstractBuilder.SupportClassType.PRIMITIVE_OBJECT.isSupportType(field), is(false));
    }

    @Test
    public void testIsAbstractBuilderType() throws NoSuchFieldException {

        Field field = ProductBuilder.class.getDeclaredField("id");
        assertThat(AbstractBuilder.SupportClassType.ABSTRACT_BUILDER.isSupportType(field), is(false));

        field = ProductBuilder.class.getDeclaredField("discount");
        assertThat(AbstractBuilder.SupportClassType.ABSTRACT_BUILDER.isSupportType(field), is(true));
    }

    @Test
    public void testIsListType() throws NoSuchFieldException {
        Field field = Product.class.getDeclaredField("name");
        assertThat(AbstractBuilder.SupportClassType.LIST.isSupportType(field), is(false));

        field = Product.class.getDeclaredField("images");
        assertThat(AbstractBuilder.SupportClassType.LIST.isSupportType(field), is(true));
    }

    @Test
    public void testIsPrimitiveType() throws NoSuchFieldException {
        Field field = ProductBuilder.class.getDeclaredField("id");
        assertThat(AbstractBuilder.SupportClassType.PRIMITIVE.isSupportType(field), is(true));

        field = ProductBuilder.class.getDeclaredField("discount");
        assertThat(AbstractBuilder.SupportClassType.PRIMITIVE.isSupportType(field), is(false));
    }
}