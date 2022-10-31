package com.onlineenergyutilityplatform.db.repositories;

import com.onlineenergyutilityplatform.db.model.Device;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Repository class used for db access
 */
@Repository
public interface DeviceRepository extends JpaRepository<Device, Integer> {
    @Query("SELECT d FROM Device d WHERE d.user IS NULL")
    List<Device> findAllUnassigned();
}
