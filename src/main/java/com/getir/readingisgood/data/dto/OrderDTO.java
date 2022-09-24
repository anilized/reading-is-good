package com.getir.readingisgood.data.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.getir.readingisgood.data.entity.OrderDetail;

import javax.validation.Valid;
import javax.validation.constraints.Null;
import java.io.Serializable;
import java.util.Date;
import java.util.Set;

public class OrderDTO implements Serializable {

    @Null
    @JsonDeserialize
    @JsonProperty(access = JsonProperty.Access.READ_ONLY)
    private Long orderId;

    @Null
    @JsonDeserialize
    @JsonProperty(access = JsonProperty.Access.READ_ONLY)
    private Long customerId;

    @Null
    @JsonProperty(access = JsonProperty.Access.READ_ONLY)
    private String status;

    @Null
    @JsonProperty(access = JsonProperty.Access.READ_ONLY)
    private Date createDate;

    @Valid
    private Set<OrderDetail> detailSet;

}
