package com.example.sample.domain.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.example.sample.domain.entity.User;
import com.example.sample.domain.repository.UserRepository;

@Service
public class UserService {

    private final UserRepository repository;

    public UserService(UserRepository repository) {
        this.repository = repository;
    }

    public List<User> findAll() {
        return repository.findAll();
    }
}
