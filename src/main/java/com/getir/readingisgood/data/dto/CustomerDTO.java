package com.getir.readingisgood.data.dto;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import lombok.Builder;
import lombok.Data;
import lombok.NonNull;

import javax.validation.constraints.Email;
import javax.validation.constraints.Null;
import java.io.Serializable;


@Data
@Builder
public class CustomerDTO implements Serializable {

    @Null
    @JsonDeserialize
    @JsonProperty(access = JsonProperty.Access.READ_ONLY)
    private Long customerId;

    @Email
    private String email;

    @JsonIgnore
    @Null
    private String password;

    @NonNull
    private String name;
    @NonNull
    private String surname;
}
