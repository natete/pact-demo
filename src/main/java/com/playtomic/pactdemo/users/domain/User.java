package com.playtomic.pactdemo.users.domain;

import java.util.List;

public record User(String id, String name, List<Car> cars) {

}
