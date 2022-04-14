package com.test.automatedparkinglot.controller;

import com.test.automatedparkinglot.domain.CarRequest;
import com.test.automatedparkinglot.service.EntranceService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;

@RestController
@RequestMapping("/parking")
public class ParkingController {

    @Autowired
    private EntranceService entranceService;

    @GetMapping("/parkcar/manual")
    public String parkCar(CarRequest carDetails) {
        return entranceService.scanCar(carDetails);
    }

    @GetMapping("/parkcar/automated")
    public String parkBunchOfCars(Integer amountOfCars) throws IOException {
        return entranceService.scanCar(amountOfCars);
    }
}
