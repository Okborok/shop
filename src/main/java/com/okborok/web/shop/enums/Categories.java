package com.okborok.web.shop.enums;

/**
 * Created on 02.11.2017.
 */
public enum Categories {
    WOOD("category.wood"),
    GLASS("category.glass"),
    RESIN("category.resin");

    private String name;

    Categories(final String name) {
        this.name = name;
    }

    public String getName() {
        return I18n.getMessage(name);
    }
}
