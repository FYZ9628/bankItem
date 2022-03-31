package com.example.base.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class HellowController {

    @RequestMapping
    public String hello() {
        return "hello world";
    }
}
