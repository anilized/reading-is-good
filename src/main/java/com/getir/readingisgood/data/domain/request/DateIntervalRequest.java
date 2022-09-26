package com.getir.readingisgood.data.domain.request;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.NonNull;
import org.springframework.format.annotation.DateTimeFormat;

import java.util.Date;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class DateIntervalRequest {
    @NonNull
    @DateTimeFormat(iso = DateTimeFormat.ISO.DATE)
    private Date startDate;
    @NonNull
    @DateTimeFormat(iso = DateTimeFormat.ISO.DATE)
    private Date endDate;

}
