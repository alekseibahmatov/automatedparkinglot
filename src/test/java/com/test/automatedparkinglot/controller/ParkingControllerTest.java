package com.test.automatedparkinglot.controller;

import com.test.automatedparkinglot.domain.CarRequest;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.jdbc.Sql;

import java.io.IOException;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@DirtiesContext(classMode = DirtiesContext.ClassMode.AFTER_EACH_TEST_METHOD)
class ParkingControllerTest {

    @LocalServerPort
    private int port;

    @Autowired
    private ParkingController parkingController;

    @Test
    @Sql(statements = {
            "INSERT INTO FLOORS (MAX_FLOOR_WEIGHT, MAX_FLOOR_HEIGHT, PRICE_FOR_TON_PER_MINUTE) VALUES (13000, 200, 5.95)",
            "INSERT INTO CARS (HEIGHT, WEIGHT, PRICE_PER_MINUTE, FLOOR_ID) VALUES (180, 2200, 12.0, 1)",
            "INSERT INTO CARS (HEIGHT, WEIGHT, PRICE_PER_MINUTE, FLOOR_ID) VALUES (190, 1800, 8.55, 1)",
            "INSERT INTO CARS (HEIGHT, WEIGHT, PRICE_PER_MINUTE, FLOOR_ID) VALUES (200, 2150, 11.23, 1)",
            "INSERT INTO CARS (HEIGHT, WEIGHT, PRICE_PER_MINUTE, FLOOR_ID) VALUES (198, 2500, 13.78, 1)",
            "INSERT INTO CARS (HEIGHT, WEIGHT, PRICE_PER_MINUTE, FLOOR_ID) VALUES (172, 2350, 13.12, 1)"
    }, executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
    @Sql(statements = {
            "DROP TABLE CARS",
            "DROP TABLE FLOORS"
    }, executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD)
    void parkCar_sameFloor() throws IOException {
        String response = parkingController.parkCar(new CarRequest(172, 1800));
        assertAll(
                () -> assertNotNull(response),
                () -> assertTrue(response.contains("Car is successfully parked at floor 1"))
        );
    }

    @Test
    @Sql(statements = {
            "INSERT INTO FLOORS (MAX_FLOOR_WEIGHT, MAX_FLOOR_HEIGHT, PRICE_FOR_TON_PER_MINUTE) VALUES (13000, 200, 5.95)",
            "INSERT INTO CARS (HEIGHT, WEIGHT, PRICE_PER_MINUTE, FLOOR_ID) VALUES (180, 2200, 12.0, 1)",
            "INSERT INTO CARS (HEIGHT, WEIGHT, PRICE_PER_MINUTE, FLOOR_ID) VALUES (190, 1800, 8.55, 1)",
            "INSERT INTO CARS (HEIGHT, WEIGHT, PRICE_PER_MINUTE, FLOOR_ID) VALUES (200, 2150, 11.23, 1)",
            "INSERT INTO CARS (HEIGHT, WEIGHT, PRICE_PER_MINUTE, FLOOR_ID) VALUES (198, 2500, 13.78, 1)",
            "INSERT INTO CARS (HEIGHT, WEIGHT, PRICE_PER_MINUTE, FLOOR_ID) VALUES (172, 2350, 13.12, 1)",
            "INSERT INTO CARS (HEIGHT, WEIGHT, PRICE_PER_MINUTE, FLOOR_ID) VALUES (172, 1800, 8.55, 1)"
    }, executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
    @Sql(statements = {
            "DROP TABLE CARS",
            "DROP TABLE FLOORS"
    }, executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD)
    void parkCar_nextFloorByWeight() throws IOException {
        String response = parkingController.parkCar(new CarRequest(190, 2000));
        assertAll(
                () -> assertNotNull(response),
                () -> assertTrue(response.contains("Car is successfully parked at floor 2"))
        );
    }

    @Test
    @Sql(statements = {
            "INSERT INTO FLOORS (MAX_FLOOR_WEIGHT, MAX_FLOOR_HEIGHT, PRICE_FOR_TON_PER_MINUTE) VALUES (13000, 200, 5.95)",
            "INSERT INTO CARS (HEIGHT, WEIGHT, PRICE_PER_MINUTE, FLOOR_ID) VALUES (180, 2200, 12.0, 1)",
            "INSERT INTO CARS (HEIGHT, WEIGHT, PRICE_PER_MINUTE, FLOOR_ID) VALUES (190, 1800, 8.55, 1)",
            "INSERT INTO CARS (HEIGHT, WEIGHT, PRICE_PER_MINUTE, FLOOR_ID) VALUES (200, 2150, 11.23, 1)",
            "INSERT INTO CARS (HEIGHT, WEIGHT, PRICE_PER_MINUTE, FLOOR_ID) VALUES (198, 2500, 13.78, 1)",
            "INSERT INTO CARS (HEIGHT, WEIGHT, PRICE_PER_MINUTE, FLOOR_ID) VALUES (172, 2350, 13.12, 1)"
    }, executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
    @Sql(statements = {
            "DROP TABLE CARS",
            "DROP TABLE FLOORS"
    }, executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD)
    void parkCar_nextFloorByHeight() throws IOException {
        String response = parkingController.parkCar(new CarRequest(210, 1800));
        assertAll(
                () -> assertNotNull(response),
                () -> assertTrue(response.contains("Car is successfully parked at floor 2"))
        );
    }

    @Test
    void parkCar_wrongInput() {
        assertThrows(IOException.class, ()-> parkingController.parkCar(new CarRequest(null, null)));
        assertThrows(IOException.class, ()-> parkingController.parkCar(new CarRequest(110, 800)));
        assertThrows(IOException.class, ()-> parkingController.parkCar(new CarRequest(340, 3501)));
    }
}
