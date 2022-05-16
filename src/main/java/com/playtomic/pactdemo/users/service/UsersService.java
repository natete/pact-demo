package com.playtomic.pactdemo.users.service;

import com.playtomic.pactdemo.users.api.requests.UserRequestBody;
import com.playtomic.pactdemo.users.dao.UsersRepository;
import com.playtomic.pactdemo.users.domain.User;
import org.springframework.stereotype.Service;

@Service
public class UsersService {

    private final UsersRepository repository;

    private final CarsServiceClient carsServiceClient;

    public UsersService(UsersRepository repository, CarsServiceClient carsServiceClient) {
        this.repository = repository;
        this.carsServiceClient = carsServiceClient;
    }


    public User getUser(String id, boolean withCars) {
        var user = repository.findById(id);

        if (withCars) {
            var carsByUser = carsServiceClient.getCarsByUser(id);
            user = new User(user.id(), user.name(), carsByUser);
        }

        return user;
    }

    public User saveUser(UserRequestBody body) {
        return repository.save(body.name());
    }
}
