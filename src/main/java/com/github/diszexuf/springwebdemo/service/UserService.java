package com.github.diszexuf.springwebdemo.service;

import com.github.diszexuf.springwebdemo.exception.UserNotFoundException;
import com.github.diszexuf.springwebdemo.model.User;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class UserService {
    private static final Map<String, User> USERS = new HashMap<>();

    public Collection<User> findAll() {
        return USERS.values();
    }

    public User findById(String id) {
        var user = USERS.get(id);

        if (user == null) {
            throw new UserNotFoundException("User not found ID: " + id);
        }

        return user;
    }

    public User create(User user) {
        var id = UUID.randomUUID().toString();
        user.setId(id);

        USERS.put(id, user);

        return user;
    }

    public User update(String userId, User userForUpdate) {
        var user = USERS.get(userId);

        if (userForUpdate.getName() != null && !Objects.equals(user.getName(), userForUpdate.getName())) {
            user.setName(userForUpdate.getName());
        }

        if (userForUpdate.getAge() != null && !Objects.equals(user.getAge(), userForUpdate.getAge())) {
            user.setAge(userForUpdate.getAge());
        }

        return user;
    }

    public void deleteById(String id) {
        USERS.remove(id);
    }
}
