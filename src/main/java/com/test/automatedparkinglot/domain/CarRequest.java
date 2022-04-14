package com.test.automatedparkinglot.domain;

import java.io.IOException;

public class CarRequest {

    private final Integer height, weight;

    public CarRequest(Integer height, Integer weight) throws IOException {
        if(height == null || weight == null || height < 130 || height > 330 || weight < 900 || weight > 3500) throw new IOException();

        this.height = height;
        this.weight = weight;
    }

    public Integer getHeight() {
        return height;
    }

    public Integer getWeight() {
        return weight;
    }
}
