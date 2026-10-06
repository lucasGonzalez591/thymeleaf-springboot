package com.lucas.springboot.app.services;

import com.lucas.springboot.app.models.User;

import java.util.List;
import java.util.Optional;

public interface UserService {

    List<User> findAll();
    Optional<User> findByID(Long id);
    User save(User user);
    void delete(Long id);


}
