package com.example.fingol1.controllers;

import com.example.fingol1.models.Response;
import com.example.fingol1.repositories.UserRepository;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.LinkedHashMap;

@RestController
public class UserController {
    private final UserRepository userRepository;

    public UserController(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @GetMapping("register")
    public Response registerUser(@RequestParam String name) {
        userRepository.registerUser(name);
        int registrations = userRepository.getUserRegistrations(name);
        return new Response("OK", registrations);
    }

    @GetMapping("stats")
    public LinkedHashMap<String, Integer> getStats(@RequestParam(required = false) String mode) {
        return userRepository.getTop3Users();
    }
}
