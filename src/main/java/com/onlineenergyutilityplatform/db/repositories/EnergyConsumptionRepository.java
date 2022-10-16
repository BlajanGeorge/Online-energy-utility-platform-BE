package com.onlineenergyutilityplatform.db.repositories;

import com.onlineenergyutilityplatform.db.model.EnergyConsumption;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface EnergyConsumptionRepository extends JpaRepository<EnergyConsumption, Integer> {
}
