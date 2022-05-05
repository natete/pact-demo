package com.playtomic.pactdemo.domain;

import java.util.List;

public record User(String id, String name, boolean condition, List<Car> cars) {

}
