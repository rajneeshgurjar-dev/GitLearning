package com.example.zoom.service;

import com.example.zoom.dto.UserRequestDto;
import com.example.zoom.dto.UserResponseDto;

import java.util.List;

public interface UserService {

    UserResponseDto createUser(UserRequestDto dto);

    List<UserResponseDto> getAllUsers();

    UserResponseDto getUserById(String id);

    UserResponseDto updateUser(String id, UserRequestDto dto);

    void deleteUser(String id);
}
