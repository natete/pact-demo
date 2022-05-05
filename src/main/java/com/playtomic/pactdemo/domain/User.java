package com.playtomic.pactdemo.domain;

import java.util.List;

public record User(String id, String name, List<Car> cars) {

}
