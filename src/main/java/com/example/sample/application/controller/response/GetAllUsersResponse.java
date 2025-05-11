package com.example.sample.application.controller.response;

import java.util.List;

import com.example.sample.domain.entity.User;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class GetAllUsersResponse {
    private long totalCount;
    private List<User> users;
}

