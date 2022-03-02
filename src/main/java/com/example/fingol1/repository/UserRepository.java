package com.example.fingol1.repository;

import org.springframework.stereotype.Repository;

import java.util.*;
import java.util.Map.Entry;
import java.util.stream.Collectors;

@Repository
public class UserRepository {
    private HashMap<String, Integer> users = new HashMap<>();

    public void registerUser(String name) {
        if (users.containsKey(name)) {
            users.replace(name, users.get(name) + 1);
        } else {
            users.put(name, 1);
        }
    }

    public void deleteUser(String name) {
        users.remove(name);
    }

    public int getUserRegistrations(String user) {
        return users.get(user);
    }

    public LinkedHashMap<String, Integer> getAllUsers() {
        return users.entrySet()
                .stream()
                .sorted(Entry.comparingByValue(Comparator.reverseOrder()))
                .collect(Collectors.toMap(
                        Entry::getKey,
                        Entry::getValue,
                        (oldValue, newValue) -> oldValue,
                        LinkedHashMap::new));
    }

    public LinkedHashMap<String, Integer> getTop3Users() {
        return users.entrySet()
                .stream()
                .sorted(Entry.comparingByValue(Comparator.reverseOrder()))
                .limit(3)
                .collect(Collectors.toMap(
                        Entry::getKey,
                        Entry::getValue,
                        (oldValue, newValue) -> oldValue,
                        LinkedHashMap::new));
    }

    public LinkedHashMap<String, Integer> getAllUsersIgnoreCase() {
        return users.entrySet()
                .stream()
                .sorted(Entry.comparingByValue(Comparator.reverseOrder()))
                .collect(Collectors.toMap(
                        entry -> entry.getKey().toUpperCase(),
                        Entry::getValue,
                        Integer::sum,
                        LinkedHashMap::new));
    }
}
