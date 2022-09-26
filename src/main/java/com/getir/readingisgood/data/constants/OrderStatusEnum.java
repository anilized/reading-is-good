package com.getir.readingisgood.data.constants;

public enum OrderStatusEnum {

    PENDING ("PENDING"),
    CANCELLED ("CANCELLED"),
    PROCESSING ("PROCESSING"),
    SHIPPED ("SHIPPED");

    private final String status;

    public String getStatus() {
        return status;
    }

    private OrderStatusEnum(String status) {
        this.status = status;
    }


}
