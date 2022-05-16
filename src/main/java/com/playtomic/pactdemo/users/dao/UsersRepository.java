package com.playtomic.pactdemo.users.dao;

import com.playtomic.pactdemo.users.domain.User;
import com.playtomic.pactdemo.users.domain.UserNotFoundException;
import java.util.ArrayList;
import java.util.List;
import org.springframework.stereotype.Service;

@Service
public class UsersRepository {

    private static final List<User> users = new ArrayList<>();

    public User findById(String id) {
        return users.stream().filter(u -> u.id().equals(id))
             .findFirst()
             .orElseThrow(UserNotFoundException::new);
    }

    public User save(String name) {
        var user = new User(String.valueOf(users.size() + 1), name, null);
        users.add(user);
        return user;
    }

    public void clear() {
        users.clear();
    }
}
