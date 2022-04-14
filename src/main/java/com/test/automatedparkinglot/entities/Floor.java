package com.test.automatedparkinglot.entities;

import javax.persistence.*;
import java.util.List;

@Entity
@Table(name = "floors")
public class Floor {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private Integer maxFloorWeight, maxFloorHeight;

    @Column(columnDefinition="DECIMAL(4,2)")
    private Double priceForTonPerMinute;

    @OneToMany(mappedBy = "floor")
    private List<Car> cars;

    public Floor(Integer maxFloorWeight, Integer maxFloorHeight, Double priceForTonPerMinute) {
        this.maxFloorWeight = maxFloorWeight;
        this.maxFloorHeight = maxFloorHeight;
        this.priceForTonPerMinute = priceForTonPerMinute;
    }

    public Floor() {}

    public Integer getMaxFloorWeight() {
        return maxFloorWeight;
    }

    public void setMaxFloorWeight(Integer maxFloorWeight) {
        this.maxFloorWeight = maxFloorWeight;
    }

    public Integer getMaxFloorHeight() {
        return maxFloorHeight;
    }

    public void setMaxFloorHeight(Integer maxFloorHeight) {
        this.maxFloorHeight = maxFloorHeight;
    }

    public Double getPriceForTonPerMinute() {
        return priceForTonPerMinute;
    }

    public void setPriceForTonPerMinute(Double priceForTonPerMinute) {
        this.priceForTonPerMinute = priceForTonPerMinute;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public List<Car> getCars() {
        return cars;
    }

    public void setCars(List<Car> cars) {
        this.cars = cars;
    }
}
