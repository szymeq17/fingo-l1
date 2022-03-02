package com.example.fingol1.repositories;

import org.springframework.stereotype.Repository;

import java.util.*;
import java.util.stream.Collectors;

@Repository
public class UserRepository {
    private HashMap<String, Integer> users = new HashMap<>();

    public HashMap<String, Integer> getUsers() {
        return users;
    }

    public void registerUser(String user) {
        if (users.containsKey(user)) {
            users.replace(user, users.get(user) + 1);
        } else {
            users.put(user, 1);
        }
    }

    public int getUserRegistrations(String user) {
        return users.get(user);
    }

    public LinkedHashMap<String, Integer> getTop3Users() {
        return users.entrySet()
                .stream()
                .sorted(Map.Entry.comparingByValue(Comparator.reverseOrder()))
                .limit(3)
                .collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue, (oldValue, newValue) -> oldValue, LinkedHashMap::new));
    }
}
