package com.playtomic.pactdemo.service;

import com.playtomic.pactdemo.domain.Car;
import java.util.List;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@FeignClient(name = "cars-service", url = "${cars-service.url}")
public interface CarsServiceClient {

    @GetMapping(path = "/cars/{user_id}")
    List<Car> getCarsByUser(@PathVariable("user_id") String userId);
}
