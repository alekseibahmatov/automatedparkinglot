package com.test.automatedparkinglot.entities;

import javax.persistence.*;

@Entity
@Table(name = "cars")
public class Car {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private Integer height, weight;

    @Column(columnDefinition="DECIMAL(4,2)")
    private Double pricePerMinute;

    @ManyToOne
    @JoinColumn(name = "floor_id")
    private Floor floor;

    public Car(Integer height, Integer weight, Double pricePerMinute, Floor floor) {
        this.height = height;
        this.weight = weight;
        this.pricePerMinute = pricePerMinute;
        this.floor = floor;
    }

    public Car() {}

    public Floor getFloor() {
        return floor;
    }

    public void setFloor(Floor floor) {
        this.floor = floor;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Integer getHeight() {
        return height;
    }

    public void setHeight(Integer height) {
        this.height = height;
    }

    public Integer getWeight() {
        return weight;
    }

    public void setWeight(Integer weight) {
        this.weight = weight;
    }

    public Double getPricePerMinute() {
        return pricePerMinute;
    }

    public void setPricePerMinute(Double pricePerMinute) {
        this.pricePerMinute = pricePerMinute;
    }
}
