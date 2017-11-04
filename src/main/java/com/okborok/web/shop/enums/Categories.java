package com.okborok.web.shop.enums;

import lombok.Getter;

/**
 * Created on 02.11.2017.
 */
@Getter
public enum Categories {
    WOOD("category.wood"),
    GLASS("category.glass"),
    RESIN("category.resin");

    private String name;

    Categories(final String name) {
        this.name = I18n.getMessage(name);
    }
}
