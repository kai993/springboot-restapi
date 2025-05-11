package com.example.sample.domain.repository;

import org.springframework.stereotype.Repository;

@Repository
public class HomeRepository {

    public String getHome() {
        return "home";
    }

    public String getHello() {
        return "Hello";
    }

}
