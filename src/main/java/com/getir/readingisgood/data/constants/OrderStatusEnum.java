package com.getir.readingisgood.data.constants;

public enum OrderStatusEnum {

    PENDING ("PENDING"),
    CANCELLED ("CANCELLED"),
    PROCESSING ("PROCESSING"),
    SHIPPED ("SHIPPED");

    public final String status;
    private OrderStatusEnum(String status) {
        this.status = status;
    }
}
