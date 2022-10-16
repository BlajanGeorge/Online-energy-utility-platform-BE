package com.onlineenergyutilityplatform.service;

import com.onlineenergyutilityplatform.db.model.Device;
import com.onlineenergyutilityplatform.db.model.Role;
import com.onlineenergyutilityplatform.db.model.User;
import com.onlineenergyutilityplatform.db.repositories.DeviceRepository;
import com.onlineenergyutilityplatform.db.repositories.UserRepository;
import com.onlineenergyutilityplatform.dto.*;
import com.onlineenergyutilityplatform.mappers.Mapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import javax.persistence.EntityNotFoundException;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import static com.onlineenergyutilityplatform.mappers.Mapper.mapFromDtoToEntity;
import static com.onlineenergyutilityplatform.mappers.Mapper.mapFromEntityToDto;
import static com.onlineenergyutilityplatform.utilities.Constants.DEVICE_WITH_ID_NOT_FOUND;
import static com.onlineenergyutilityplatform.utilities.Constants.USER_NOT_FOUND;

/**
 * User Service class to handle user operations
 */
@Service
@Slf4j
public class UserServiceImplementation implements UserService {
    /**
     * Repository to provide access to db
     */
    private final UserRepository userRepository;
    /**
     * Repository to provide access to db
     */
    private final DeviceRepository deviceRepository;

    public UserServiceImplementation(final UserRepository userRepository,
                                     final DeviceRepository deviceRepository) {
        this.userRepository = userRepository;
        this.deviceRepository = deviceRepository;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public PagedResult<GetUserDto> getUsers(PageRequest pageRequest) {
        final Page<User> page;
        page = userRepository.findAll(pageRequest);
        final var pageInfo = new PageInfo(pageRequest.getPageNumber(), pageRequest.getPageSize(), page.getTotalElements(), page.getTotalPages());

        log.info("Obtained user page info with size {} and index {} when querying for users.", pageInfo.getPageSize(), pageInfo.getPageIndex());


        final List<GetUserDto> data = page.stream()
                .map(Mapper::mapFromEntityToDto)
                .collect(Collectors.toList());

        log.info("Obtained user list, there are {} users.", data.size());

        return new PagedResult<>(data, pageInfo);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public GetUserDto getUserById(int userId) {
        Optional<User> userOptional = userRepository.findById(userId);
        User user = userOptional.orElseThrow(() -> new EntityNotFoundException(String.format(USER_NOT_FOUND, userId)));
        return mapFromEntityToDto(user);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public GetUserDto updateUserById(UpdateUserDto updateUserDto, int userId) {
        Optional<User> userOptional = userRepository.findById(userId);
        User user = userOptional.orElseThrow(() -> new EntityNotFoundException(String.format(USER_NOT_FOUND, userId)));

        user.setUsername(updateUserDto.getUsername());
        user.setPassword(updateUserDto.getPassword());
        user.setRole(updateUserDto.getRole());
        user.setName(updateUserDto.getName());

        return mapFromEntityToDto(userRepository.save(user));
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public GetUserDto createUser(CreateUserDto createUserDto, Role role) {
        User user = mapFromDtoToEntity(createUserDto, role);
        return mapFromEntityToDto(userRepository.save(user));
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void deleteUserById(int userId) {
        Optional<User> userOptional = userRepository.findById(userId);
        User user = userOptional.orElseThrow(() -> new EntityNotFoundException(String.format(USER_NOT_FOUND, userId)));
        userRepository.delete(user);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void assignDeviceToUser(int userId, int deviceId) {
        Optional<User> userOptional = userRepository.findById(userId);
        User user = userOptional.orElseThrow(() -> new EntityNotFoundException(String.format(USER_NOT_FOUND, userId)));
        Optional<Device> deviceOptional = deviceRepository.findById(deviceId);
        Device device = deviceOptional.orElseThrow(() -> new EntityNotFoundException(String.format(DEVICE_WITH_ID_NOT_FOUND, deviceId)));

        user.getDeviceList().add(device);

        userRepository.save(user);
    }
}