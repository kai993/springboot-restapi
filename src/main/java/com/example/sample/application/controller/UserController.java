package com.example.sample.application.controller;

import java.util.List;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.sample.application.controller.response.GetAllUsersResponse;
import com.example.sample.domain.entity.AllUser;
import com.example.sample.domain.service.UserService;

@RestController
@RequestMapping("/api/v1/users")
public class UserController {

    private final UserService service;

    public UserController(UserService service) {
        this.service = service;
    }

    // 全てのユーザーを取得
    @GetMapping
    public GetAllUsersResponse findAll() {
        List<AllUser> users = service.findAll();
        return GetAllUsersResponse.builder()
                .totalCount(users.size())
                .users(users)
                .build();
    }

    // ユーザーIDからユーザーを取得
    // @GetMapping("/{id}")
    // public User findById(@PathVariable Long id) {
    //     return service.findById(id);
    // }
}

