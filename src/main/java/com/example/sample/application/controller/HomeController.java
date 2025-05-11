package com.example.sample.application.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.sample.domain.service.HomeService;

/**
 * ホームコントローラー
 * @author example
 * @version 1.0
 */
@RestController
public class HomeController {
    Logger logger = LoggerFactory.getLogger(HomeController.class);

    HomeService service;

    public HomeController(HomeService service) {
        this.service = service;
    }

    @GetMapping("/home")
    public String home() {
        logger.info("home");
        return service.getHome();
    }
}

