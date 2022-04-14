package com.test.automatedparkinglot.service;

import com.test.automatedparkinglot.domain.CarRequest;
import com.test.automatedparkinglot.entities.Car;
import com.test.automatedparkinglot.entities.Floor;
import com.test.automatedparkinglot.repository.CarRepository;
import com.test.automatedparkinglot.repository.FloorRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.List;
import java.util.Random;

@Service
public class EntranceService {

    @Autowired
    private FloorRepository floorRepository;

    @Autowired
    private CarRepository carRepository;

    public String scanCar(CarRequest car) {
        return processCar(car);
    }

    public String scanCar(Integer amountOfCars) throws IOException {
        return generateCars(amountOfCars);
    }

    private String generateCars(Integer carAmount) throws IOException {

        Random rand = new Random();

        StringBuilder message = new StringBuilder();

        for (int i = 0; i < carAmount; i++) {

            Integer carHeight = rand.nextInt((310 - 150) + 1) + 150;
            Integer carWeight = rand.nextInt((2750 - 1300) + 1) + 1300;

            CarRequest newRequestCar = new CarRequest(carHeight, carWeight);

            message.append(processCar(newRequestCar));
        }

        return message.toString();
    }

    private String processCar(CarRequest car) {
        List<Floor> suitableFloors = floorRepository.selectSuitableFloors(car.getHeight(), car.getWeight());

        if(suitableFloors.isEmpty()) {
            Floor newFloor = createNewFloor(car);

            return parkCar(car, newFloor);
        } else {
            Floor suitableFloor = suitableFloors.get(0);

            return parkCar(car, suitableFloor);
        }
    }

    private String parkCar(CarRequest car, Floor suitableFloor) {
        Double pricePerMinute = suitableFloor.getPriceForTonPerMinute() * (double) (car.getWeight()) / 1000.0;

        Car newCar = new Car(car.getHeight(), car.getWeight(), pricePerMinute, suitableFloor);

        carRepository.save(newCar);

        return String.format("Car is successfully parked at floor %d, for %.2f € per minute <br>", suitableFloor.getId(), pricePerMinute);
    }

    private Floor createNewFloor(CarRequest car) {
        Random rand = new Random();

        Integer floorHeightOffset = rand.nextInt((30 - 10) + 1) + 10;
        Integer floorWeightOffset = rand.nextInt((12 - 5) + 1) + 5;
        double pricePerTon = 1.0 + (rand.nextDouble() * (10.0 - 1.0));

        Floor newFloor = new Floor(
                car.getWeight() * floorWeightOffset,
                car.getHeight() + floorHeightOffset,
                pricePerTon);

        floorRepository.save(newFloor);

        return newFloor;
    }
}
