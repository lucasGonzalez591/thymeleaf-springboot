package com.lucas.springboot.app.services;

import com.lucas.springboot.app.models.User;
import com.lucas.springboot.app.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
public class UserServiceImpl implements UserService{

    public UserRepository repository;

    public UserServiceImpl(UserRepository repository) {
        this.repository = repository;
    }

    @Transactional(readOnly = true)
    @Override
    public List<User> findAll() {
        return (List<User>) this.repository.findAll();
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<User> findByID(Long id) {
        return this.repository.findById(id);
    }

    @Transactional
    @Override
    public User save(User user) {
        return this.repository.save(user);
    }

    @Transactional
    @Override
    public void delete(Long id) {
        this.repository.deleteById(id);
    }
}
