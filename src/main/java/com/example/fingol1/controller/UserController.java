package com.example.fingol1.controller;

import com.example.fingol1.model.Response;
import com.example.fingol1.repository.UserRepository;
import org.springframework.web.bind.MissingServletRequestParameterException;
import org.springframework.web.bind.annotation.*;

import java.util.LinkedHashMap;

@RestController
public class UserController {
    private final UserRepository userRepository;

    public UserController(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @PostMapping("register")
    public Response registerUser(@RequestParam String name) {
        userRepository.registerUser(name);
        int registrations = userRepository.getUserRegistrations(name);
        return new Response("OK", registrations);
    }

    @DeleteMapping("delete")
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
