package com.example.sample.domain.service;

import java.sql.SQLException;
import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;

import com.example.sample.application.controller.request.UserCreateRequest;
import com.example.sample.application.controller.request.UserUpdateRequest;
import com.example.sample.domain.entity.AllUser;
import com.example.sample.domain.entity.User;
import com.example.sample.domain.exception.NotFoundUserException;
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

    public User findById(Long id) throws NotFoundUserException {
        Optional<User> user = repository.findById(id);
        if (user.isPresent()) {
            return user.get();
        } else {
            throw new NotFoundUserException("User not found");
        }
    }

    /**
     * ユーザーを作成する
     *
     * @param user ユーザー情報
     * @return true: 成功, false: 失敗
     */
    public boolean create(UserCreateRequest req) {
        return repository.create(req);
    }

    public User save(Long id, UserUpdateRequest req) throws SQLException {
        Optional<User> user = repository.update(id, req);
        if (user.isPresent()) {
            return user.get();
        } else {
            throw new SQLException();
        }
    }

    public boolean deleteById(Long id) {
        return repository.deleteById(id);
    }
}
