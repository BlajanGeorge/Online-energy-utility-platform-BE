package com.onlineenergyutilityplatform.db.repositories;

import com.onlineenergyutilityplatform.db.model.EnergyConsumption;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface EnergyConsumptionRepository extends JpaRepository<EnergyConsumption, Integer> {

    @Query("SELECT e FROM EnergyConsumption e WHERE e.device.id = :id AND e.time >= :initialDate AND e.time <= :endDate ORDER BY e.time")
    List<EnergyConsumption> getReportsForDeviceInInterval(@Param("id") int id, @Param("initialDate") Long initialDate, @Param("endDate") Long endDate);
}
