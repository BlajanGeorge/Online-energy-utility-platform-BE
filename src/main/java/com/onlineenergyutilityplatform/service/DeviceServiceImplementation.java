package com.onlineenergyutilityplatform.service;

import com.onlineenergyutilityplatform.db.model.Device;
import com.onlineenergyutilityplatform.db.model.EnergyConsumption;
import com.onlineenergyutilityplatform.db.repositories.DeviceRepository;
import com.onlineenergyutilityplatform.db.repositories.EnergyConsumptionRepository;
import com.onlineenergyutilityplatform.dto.*;
import com.onlineenergyutilityplatform.mappers.Mapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityNotFoundException;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import static com.onlineenergyutilityplatform.mappers.Mapper.mapFromDtoToEntity;
import static com.onlineenergyutilityplatform.mappers.Mapper.mapFromEntityToDto;
import static com.onlineenergyutilityplatform.utilities.Constants.DEVICE_WITH_ID_NOT_FOUND;

@Service
@Slf4j
public class DeviceServiceImplementation implements DeviceService {
    private final DeviceRepository deviceRepository;
    private final EnergyConsumptionRepository energyConsumptionRepository;

    public DeviceServiceImplementation(final DeviceRepository deviceRepository,
                                       final EnergyConsumptionRepository energyConsumptionRepository) {
        this.deviceRepository = deviceRepository;
        this.energyConsumptionRepository = energyConsumptionRepository;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public PagedResult<GetDeviceDto> getDevices(PageRequest pageRequest) {
        final Page<Device> page;
        page = deviceRepository.findAll(pageRequest);
        final var pageInfo = new PageInfo(pageRequest.getPageNumber(), pageRequest.getPageSize(), page.getTotalElements(), page.getTotalPages());

        log.info("Obtained device page info with size {} and index {} when querying for users.", pageInfo.getPageSize(), pageInfo.getPageIndex());


        final List<GetDeviceDto> data = page.stream()
                .map(Mapper::mapFromEntityToDto)
                .collect(Collectors.toList());

        log.info("Obtained device list, there are {} devices.", data.size());

        return new PagedResult<>(data, pageInfo);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public GetDeviceDto getDeviceById(int deviceId) {
        Optional<Device> deviceOptional = deviceRepository.findById(deviceId);
        Device device = deviceOptional.orElseThrow(() -> new EntityNotFoundException(String.format(DEVICE_WITH_ID_NOT_FOUND, deviceId)));
        return mapFromEntityToDto(device);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    @Transactional
    public GetDeviceDto updateDeviceById(DeviceDto deviceDto, int deviceId) {
        Optional<Device> deviceOptional = deviceRepository.findById(deviceId);
        Device device = deviceOptional.orElseThrow(() -> new EntityNotFoundException(String.format(DEVICE_WITH_ID_NOT_FOUND, deviceId)));

        device.setAddress(deviceDto.getAddress());
        device.setDescription(deviceDto.getDescription());
        device.setMaxHourlyEnergyConsumption(deviceDto.getMaxHourlyEnergyConsumption());

        return mapFromEntityToDto(deviceRepository.save(device));
    }

    /**
     * {@inheritDoc}
     */
    @Override
    @Transactional
    public GetDeviceDto createDevice(DeviceDto deviceDto) {
        Device device = mapFromDtoToEntity(deviceDto);
        return mapFromEntityToDto(deviceRepository.save(device));
    }


    /**
     * {@inheritDoc}
     */
    @Override
    @Transactional
    public void deleteDeviceById(int deviceId) {
        Optional<Device> deviceOptional = deviceRepository.findById(deviceId);
        Device device = deviceOptional.orElseThrow(() -> new EntityNotFoundException(String.format(DEVICE_WITH_ID_NOT_FOUND, deviceId)));
        deviceRepository.delete(device);
    }

    @Override
    @Transactional
    public GetDeviceDto addEnergyConsumptionReport(EnergyConsumptionDto energyConsumptionDto, int deviceId) {
        Optional<Device> deviceOptional = deviceRepository.findById(deviceId);
        Device device = deviceOptional.orElseThrow(() -> new EntityNotFoundException(String.format(DEVICE_WITH_ID_NOT_FOUND, deviceId)));

        EnergyConsumption energyConsumption = mapFromDtoToEntity(energyConsumptionDto);
        energyConsumption.setDevice(device);
        energyConsumptionRepository.save(energyConsumption);

        return mapFromEntityToDto(device);
    }
}
