package com.getir.readingisgood.data.domain.response;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.Month;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class OrderReport {
    private Integer totalOrderCount;
    private Integer totalBookCount;
    private Double totalPrice;
    @JsonIgnore
    private Integer monthIndex;
    private String month;

    public String getMonth() {
        if(monthIndex == null) {
            return null;
        }
        return Month.of(monthIndex).name();
    }

}
