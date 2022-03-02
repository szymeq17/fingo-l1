package com.example.fingol1.controller;

import com.example.fingol1.model.Response;
import com.example.fingol1.repository.UserRepository;
import org.springframework.web.bind.MissingServletRequestParameterException;
import org.springframework.web.bind.annotation.ExceptionHandler;
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
        if (name == null) {
            return new Response("PARAMETER NAME NOT FOUND", null);
        }
        userRepository.registerUser(name);
        int registrations = userRepository.getUserRegistrations(name);
        return new Response("OK", registrations);
    }

    @GetMapping("delete")
    public void deleteUser(@RequestParam String name) {
        userRepository.deleteUser(name);
    }

    @GetMapping("stats")
    public LinkedHashMap<String, Integer> getStats(@RequestParam(required = false) String mode) {
        if (mode == null) {
            return userRepository.getTop3Users();
        }
        return switch(mode) {
            case "ALL" -> userRepository.getAllUsers();
            case "IGNORE_CASE" -> userRepository.getAllUsersIgnoreCase();
            default -> userRepository.getTop3Users();
        };
    }

    @ExceptionHandler(MissingServletRequestParameterException.class)
    public String handleMissingParams(MissingServletRequestParameterException ex) {
        String name = ex.getParameterName();
        return name + " parameter is missing";
    }
}
