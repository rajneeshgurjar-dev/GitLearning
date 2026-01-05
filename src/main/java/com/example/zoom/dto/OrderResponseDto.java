package com.example.zoom.dto;

import lombok.*;
import org.bson.types.ObjectId;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class OrderResponseDto {

    private String id;
    private ObjectId userId;
    private String productName;
    private double amount;
    private LocalDateTime orderedAt;
}
