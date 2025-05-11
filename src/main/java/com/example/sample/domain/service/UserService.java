package com.example.sample.domain.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.example.sample.domain.entity.AllUser;
import com.example.sample.domain.repository.UserRepository;

@Service
public class UserService {

    private final UserRepository repository;

    public UserService(UserRepository repository) {
        this.repository = repository;
    }

    public List<AllUser> findAll() {
        return repository.findAll();
    }

    // public User findById(Long id) {
    //     return new User();
    // }
    //
    // public User save(User user) {
    //     return new User();
    // }
    //
    // public boolean deleteById(Long id) {
    //     return true;
    // }
}
