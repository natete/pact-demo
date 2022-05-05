package com.playtomic.pactdemo.api;

import com.playtomic.pactdemo.api.requests.UserRequestBody;
import com.playtomic.pactdemo.domain.User;
import com.playtomic.pactdemo.service.UsersService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(path = "/v1/users", produces="application/json")
public class UsersController {

    private final UsersService service;

    public UsersController(UsersService service) {
        this.service = service;
    }

    @GetMapping(path = "/{user_id}")
    public User getUser(@PathVariable("user_id") String userId,
                        @RequestParam(name = "with_cars", defaultValue = "false") boolean withCars) {
        return service.getUser(userId, withCars);
    }

    @PostMapping
    public User saveUser(@RequestBody UserRequestBody body) {
        return service.saveUser(body);
    }
}
