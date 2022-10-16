package com.onlineenergyutilityplatform.mappers;

import com.onlineenergyutilityplatform.db.model.Device;
import com.onlineenergyutilityplatform.db.model.EnergyConsumption;
import com.onlineenergyutilityplatform.db.model.Role;
import com.onlineenergyutilityplatform.db.model.User;
import com.onlineenergyutilityplatform.dto.*;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;

import java.util.stream.Collectors;

/**
 * Class to map from and to db user entity
 */
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class Mapper {

    /**
     * map from user dto to user entity
     *
     * @param createUserDto user dto
     * @param role          user role
     * @return {@link User}
     */
    public static User mapFromDtoToEntity(final CreateUserDto createUserDto, Role role) {
        if (createUserDto == null) {
            return null;
        }

        return new User(createUserDto.getName(),
                role,
                createUserDto.getUsername(),
                createUserDto.getPassword(),
                createUserDto.getDeviceDtoList().stream().map(Mapper::mapFromDtoToEntity).collect(Collectors.toList())
        );
    }

    /**
     * map from user entity to {@link GetUserDto}
     *
     * @param user user entity
     * @return {@link GetUserDto}
     */
    public static GetUserDto mapFromEntityToDto(final User user) {
        if (user == null) {
            return null;
        }

        return new GetUserDto(user.getId(),
                user.getName(),
                user.getRole(),
                user.getUsername(),
                user.getPassword(),
                user.getDeviceList().stream().map(Mapper::mapFromEntityToDto).collect(Collectors.toList())
        );
    }

    public static Device mapFromDtoToEntity(final DeviceDto deviceDto) {
        if (deviceDto == null) {
            return null;
        }

        return new Device(deviceDto.getDescription(),
                deviceDto.getAddress(),
                deviceDto.getMaxHourlyEnergyConsumption());
    }

    public static GetDeviceDto mapFromEntityToDto(final Device device) {
        if (device == null) {
            return null;
        }

        User user = device.getUser();

        return new GetDeviceDto(device.getId(), device.getDescription(), device.getAddress(), device.getMaxHourlyEnergyConsumption(), user == null ? null : user.getId(), device.getEnergyConsumptionList().stream().map(Mapper::mapFromEntityToDto).collect(Collectors.toList()));
    }

    public static EnergyConsumptionDto mapFromEntityToDto(final EnergyConsumption energyConsumption) {
        if (energyConsumption == null) {
            return null;
        }

        return new EnergyConsumptionDto(energyConsumption.getEnergy(), energyConsumption.getTime());
    }

    public static EnergyConsumption mapFromDtoToEntity(final EnergyConsumptionDto energyConsumptionDto) {
        if (energyConsumptionDto == null) {
            return null;
        }

        return new EnergyConsumption(energyConsumptionDto.getEnergy(), energyConsumptionDto.getTime());
    }
}
