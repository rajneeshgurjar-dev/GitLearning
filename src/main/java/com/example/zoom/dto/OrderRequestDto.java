package com.example.zoom.dto;

import lombok.Data;

@Data
public class OrderRequestDto {

    private String productName;
    private double amount;
}