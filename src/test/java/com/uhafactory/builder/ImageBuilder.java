package com.uhafactory.builder;

import sample.Image;

/**
 * Created by lineplus on 2016. 6. 1..
 */
public class ImageBuilder extends AbstractBuilder<Image> {
    private String url;

    private String comment;

    public static ImageBuilder anImage() {
        return new ImageBuilder();
    }

    public ImageBuilder withUrl(String url) {
        this.url = url;
        return this;
    }
}
