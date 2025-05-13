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

    public User save(Long id, UserUpdateRequest req) throws NotFoundUserException, SQLException {
        try {
            Optional<User> user = repository.update(id, req);
            if (user.isPresent()) {
                return user.get();
            } else {
                throw new SQLException("ユーザーの更新に失敗しました: " + id);
            }
        } catch (NotFoundUserException e) {
            throw new NotFoundUserException("ユーザーが見つかりません: " + id, e);
        } catch (SQLException e) {
            throw new SQLException("ユーザーの更新に失敗しました: " + id, e);
        }
    }

    public boolean deleteById(Long id) throws Exception {
        try {
            return repository.deleteById(id);
        } catch (NotFoundUserException e) {
            throw new NotFoundUserException("ユーザーが見つかりません: " + id, e);
        }
    }
}
