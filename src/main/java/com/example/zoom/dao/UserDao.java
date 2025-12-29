package com.example.zoom.dao;

import com.example.zoom.entity.User;

import java.util.List;
import java.util.Optional;

public interface UserDao {

    User save(User user);

    List<User> findAll();

    Optional<User> findById(String id);

    void deleteById(String id);
}
