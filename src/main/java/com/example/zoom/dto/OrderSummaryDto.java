package com.example.zoom.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class OrderSummaryDto {

    private String id;
    private String productName;
    private double amount;
    private LocalDateTime orderedAt;
}


