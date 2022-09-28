package com.getir.readingisgood.data.dto;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Null;
import java.io.Serializable;


@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class CustomerDTO implements Serializable {

    @Null
    @JsonDeserialize
    @JsonProperty(access = JsonProperty.Access.READ_ONLY)
    private Long customerId;

    @Email(message = "field must be provided with email format")
    @Schema(description = "Customer email", example = "johndoe@gmail.com")
    private String email;

    @JsonIgnore
    @Null
    @Schema(description = "Customer password")
    @Parameter(hidden = true)
    private String password;

    @NotBlank(message = "name must be provided")
    @Schema(description = "Customer name", example = "John")
    private String name;
    @NotBlank(message = "surname must be provided")
    @Schema(description = "Customer name", example = "Doe")
    private String surname;
}
