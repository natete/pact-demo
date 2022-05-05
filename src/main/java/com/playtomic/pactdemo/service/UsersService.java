package com.playtomic.pactdemo.service;

import com.playtomic.pactdemo.api.requests.UserRequestBody;
import com.playtomic.pactdemo.domain.User;
import java.util.ArrayList;
import java.util.List;
import javax.annotation.PostConstruct;
import org.springframework.stereotype.Service;

@Service
public class UsersService {

    private static final List<User> users = new ArrayList<>();

    private final CarsServiceClient carsServiceClient;

    public UsersService(CarsServiceClient carsServiceClient) {
        this.carsServiceClient = carsServiceClient;
    }

    @PostConstruct
    void setup() {
        users.add(new User("1", "tom", null));
    }


    public User getUser(String id, boolean withCars) {
        var user = users.stream().filter(u -> u.id().equals(id))
                         .findFirst()
                         .orElseThrow(RuntimeException::new);

        if (withCars) {
            var carsByUser = carsServiceClient.getCarsByUser(id);
            user = new User(user.id(), user.name(), carsByUser);
        }

        return user;
    }

    public User saveUser(UserRequestBody body) {
        var user = new User(String.valueOf(users.size() + 1), body.name(), null);
        users.add(user);
        return user;
    }
}
