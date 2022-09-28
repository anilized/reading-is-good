package com.getir.readingisgood.data.constants;

public enum OrderStatusEnum {

    PENDING ("PENDING");

    private final String status;

    public String getStatus() {
        return status;
    }

    private OrderStatusEnum(String status) {
        this.status = status;
    }


}
