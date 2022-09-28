package com.getir.readingisgood.logging;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.Fetch;

import javax.persistence.*;
import java.util.Date;

@Entity
@Table(name = "applogs")
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class LogEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    private String endpoint;

    private String method;

    private String status;

    private String message;

    private Long userId;

    private Date timeStamp;

    private String errorType;

    private String operation;
    @Column(columnDefinition = "TEXT")
    private String response;
}
