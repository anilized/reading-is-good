package com.getir.readingisgood.data.domain.response;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.http.HttpStatus;

import java.util.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ErrorResponse {
    @Schema(description = "Request time", type = "Date", example = "2022-09-27T18:36:02.154+00:00")
    private Date timeStamp;
    @Schema(description = "Error code", example = "BF_NO_AVAILABLE_STOCK")
    private String code;
    @Schema(description = "Message", example = "Stock not found")
    private String message;
    @Schema(description = "Http Status", example = "404")
    private HttpStatus status;
}
