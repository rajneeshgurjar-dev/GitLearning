package com.example.zoom.dto;

import lombok.Data;

@Data
public class UserRequestDto {
    private String name;
    private String email;
    private int age;
    private AddressDto address;
}
