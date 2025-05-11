package com.example.sample.domain.entity;

import java.time.LocalDate;
import java.time.LocalDateTime;

import lombok.Data;

@Data
public class User {
    private Long id;
    private String username;
    private String password; // @TODO: 暗号化する
    private LocalDate birthdate;
    private String firstName;
    private String lastName;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
}

