package com.example.util;

public enum Roles {
    ADMIN("admin"),
    SELLER("seller"),
    CUSTOMER("customer");

    private final String value;

    Roles(String value) {
        this.value = value;
    }

    public String getValue() {
        return value;
    }
}
