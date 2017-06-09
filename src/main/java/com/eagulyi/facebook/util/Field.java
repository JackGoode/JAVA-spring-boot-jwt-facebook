package com.eagulyi.facebook.util;

/**
 * Created by eugene on 5/17/17.
 */
public enum Field {
    FIRST_NAME("first_name"), LAST_NAME("last_name"), EDUCATION("education"), EMAIL("email"), WORK("work"), LOCATION("location{location{city, country}}");

    private final String name;

    Field(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }
}
