package com.example.zoom.serviceImpl;

import com.example.zoom.dao.UserDao;
import com.example.zoom.dto.UserRequestDto;
import com.example.zoom.dto.UserResponseDto;
import com.example.zoom.entity.User;
import com.example.zoom.exception.ResourceNotFoundException;
import com.example.zoom.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

    private final UserDao userDao;

    @Override
    public UserResponseDto createUser(UserRequestDto dto) {
        User user = new User(null, dto.getName(), dto.getEmail(), dto.getAge());
        return mapToResponse(userDao.save(user));
    }

    @Override
    public List<UserResponseDto> getAllUsers() {
        return userDao.findAll()
                .stream()
                .map(this::mapToResponse)
                .collect(Collectors.toList());
    }

    @Override
    public UserResponseDto getUserById(String id) {
        User user = userDao.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("User not found with id: " + id));
        return mapToResponse(user);
    }

    @Override
    public UserResponseDto updateUser(String id, UserRequestDto dto) {
        User user = userDao.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("User not found with id: " + id));
        if(dto.getName()!=null)
            user.setName(dto.getName());
        if(dto.getEmail()!=null)
            user.setEmail(dto.getEmail());
        if(dto.getAge()!=0)
            user.setAge(dto.getAge());

        return mapToResponse(userDao.save(user));
    }

    @Override
    public void deleteUser(String id) {
        userDao.deleteById(id);
    }

    private UserResponseDto mapToResponse(User user) {
        return new UserResponseDto(
                user.getId(),
                user.getName(),
                user.getEmail(),
                user.getAge()
        );
    }
}
