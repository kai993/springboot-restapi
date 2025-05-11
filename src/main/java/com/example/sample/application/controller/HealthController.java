package com.example.sample.application.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

// @TODO: actuatorに移行する
// https://spring.pleiades.io/spring-boot/reference/actuator/endpoints.html
@RestController
public class HealthController {

    @GetMapping("/health")
    public String health() {
        return "ok";
    }

}
