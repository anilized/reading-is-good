package com.getir.readingisgood.data.dto;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import lombok.Builder;
import lombok.Data;
import lombok.NonNull;

import javax.validation.constraints.Null;
import java.io.Serializable;

@Data
@Builder
public class BookDTO implements Serializable {

    @JsonDeserialize
    @JsonProperty(access = JsonProperty.Access.READ_ONLY)
    private Long bookId;

    private String name;
    private String authorName;

    private int stock;
    private double price;
    @JsonIgnore
    private Long version;


}
