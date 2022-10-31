package com.onlineenergyutilityplatform.service;

import com.onlineenergyutilityplatform.db.model.Role;
import com.onlineenergyutilityplatform.dto.*;
import org.springframework.data.domain.PageRequest;

import java.util.List;

/**
 * Device Service class to handle device operations
 */
public interface DeviceService {

    /**
     * Return a paginated request of device
     *
     * @return {@link PagedResult}
     */
    PagedResult<GetDeviceDto> getDevices(PageRequest pageRequest);

    /**
     * Return user by id if exist
     *
     * @param deviceId device identifier
     * @return {@link GetDeviceDto}
     */
    GetDeviceDto getDeviceById(int deviceId);

    /**
     * Update device by id if exist
     *
     * @param deviceId user identifier
     * @return {@link GetDeviceDto}
     */
    GetDeviceDto updateDeviceById(DeviceDto deviceDto, int deviceId);

    /**
     * Create device
     *
     * @param deviceDto create device request dto
     * @return {@link GetDeviceDto}
     */
    GetDeviceDto createDevice(DeviceDto deviceDto);

    /**
     * Delete device by id if exist
     *
     * @param deviceId user identifier
     */
    void deleteDeviceById(int deviceId);

    GetDeviceDto addEnergyConsumptionReport(EnergyConsumptionDto energyConsumptionDto, int deviceId);

    List<GetDeviceDto> getUnassignedDevices();
}
