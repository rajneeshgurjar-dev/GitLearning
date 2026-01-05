package com.example.zoom.serviceImpl;

import com.example.zoom.dao.OrderDao;
import com.example.zoom.dao.UserDao;
import com.example.zoom.dto.AddressDto;
import com.example.zoom.dto.UserRequestDto;
import com.example.zoom.dto.UserResponseDto;
import com.example.zoom.dto.UserWithOrdersDto;
import com.example.zoom.entity.Address;
import com.example.zoom.entity.User;
import com.example.zoom.exception.ResourceNotFoundException;
import com.example.zoom.repository.UserAggregationRepository;
import com.example.zoom.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

    private final UserDao userDao;
    private final OrderDao orderDao;
    private final UserAggregationRepository userAggregationRepository;

    @Override
    public UserResponseDto createUser(UserRequestDto dto) {
        User user = new User(null, dto.getName(), dto.getEmail(), dto.getAge(),mapToAddress(dto.getAddress()));
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
        if(dto.getAddress()!=null){
            if(dto.getAddress().getStreet()!=null)user.getAddress().setStreet(dto.getAddress().getStreet());
            if(dto.getAddress().getCity()!=null)user.getAddress().setCity(dto.getAddress().getCity());
            if(dto.getAddress().getState()!=null)user.getAddress().setState(dto.getAddress().getState());
            if(dto.getAddress().getPincode()!=null)user.getAddress().setPincode(dto.getAddress().getPincode());
        }

        return mapToResponse(userDao.save(user));
    }

    @Override
    public void deleteUser(String userId) {
        userDao.findById(userId)
                .orElseThrow(() ->
                        new ResourceNotFoundException("User not found with id: " + userId));

        orderDao.deleteByUserId(userId);

        userDao.deleteById(userId);
    }

    @Override
    public UserWithOrdersDto getUserWithOrders(String userId) {

        UserWithOrdersDto result =
                userAggregationRepository.getUserWithOrders(userId);

        if (result == null) {
            throw new ResourceNotFoundException(
                    "User not found with id: " + userId);
        }

        return result;
    }

    private UserResponseDto mapToResponse(User user) {
        return new UserResponseDto(
                user.getId(),
                user.getName(),
                user.getEmail(),
                user.getAge(),
                user.getAddress() != null ? mapToAddressDto(user.getAddress()) : null
        );
    }
    private AddressDto mapToAddressDto(Address address) {
        return new AddressDto(
                address.getStreet(),
                address.getCity(),
                address.getState(),
                address.getPincode()
        );
    }
    private Address mapToAddress(AddressDto dto) {
        return new Address(
                dto.getStreet(),
                dto.getCity(),
                dto.getState(),
                dto.getPincode()
        );
    }
}
