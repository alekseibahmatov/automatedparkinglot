package com.test.automatedparkinglot.repository;

import com.test.automatedparkinglot.entities.Floor;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface FloorRepository extends JpaRepository<Floor, Long> {

    @Query(value = "SELECT f.id, f.MAX_FLOOR_WEIGHT, f.MAX_FLOOR_HEIGHT, f.PRICE_FOR_TON_PER_MINUTE FROM FLOORS f INNER JOIN CARS c on f.ID = c.FLOOR_ID WHERE f.MAX_FLOOR_HEIGHT >= :height GROUP BY f.ID HAVING SUM(c.WEIGHT) + :weight <= f.MAX_FLOOR_WEIGHT", nativeQuery = true)
    List<Floor> selectSuitableFloors(@Param("height") Integer height, @Param("weight") Integer weight);
}
