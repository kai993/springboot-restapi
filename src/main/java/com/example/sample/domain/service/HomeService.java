package com.example.sample.domain.service;

import org.springframework.stereotype.Service;

import com.example.sample.config.AppProperties;
import com.example.sample.domain.repository.HomeRepository;

@Service
public class HomeService {

    HomeRepository repository;
    AppProperties properties;

    public HomeService(HomeRepository repository, AppProperties properties) {
        this.repository = repository;
        this.properties = properties;
    }

    public String getHome() {
        String hello = repository.getHello();
        String key1 = properties.getKey1();
        String content = hello + " " + key1 + "!";

        // LocalTime now = LocalTime.now();
        // if (now.getMinute() % 2 == 0) {
        //     throw new SampleServiceException("Service exception occurred");
        // }

        return content;
    }

}
