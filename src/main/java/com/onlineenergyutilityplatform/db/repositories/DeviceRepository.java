package com.onlineenergyutilityplatform.db.repositories;

import com.onlineenergyutilityplatform.db.model.Device;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * Repository class used for db access
 */
public interface DeviceRepository extends JpaRepository<Device, Integer> {
}
