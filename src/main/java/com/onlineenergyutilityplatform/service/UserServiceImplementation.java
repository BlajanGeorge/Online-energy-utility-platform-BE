package com.onlineenergyutilityplatform.service;

import com.onlineenergyutilityplatform.db.model.Device;
import com.onlineenergyutilityplatform.db.model.Role;
import com.onlineenergyutilityplatform.db.model.User;
import com.onlineenergyutilityplatform.db.repositories.DeviceRepository;
import com.onlineenergyutilityplatform.db.repositories.UserRepository;
import com.onlineenergyutilityplatform.dto.*;
import com.onlineenergyutilityplatform.mappers.Mapper;
import com.onlineenergyutilityplatform.security.TokenService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityNotFoundException;
import java.util.List;
import java.util.Objects;
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
    private final BCryptPasswordEncoder bCryptPasswordEncoder;
    private final TokenService tokenService;

    public UserServiceImplementation(final UserRepository userRepository,
                                     final DeviceRepository deviceRepository,
                                     final BCryptPasswordEncoder bCryptPasswordEncoder,
                                     final TokenService tokenService) {
        this.userRepository = userRepository;
        this.deviceRepository = deviceRepository;
        this.bCryptPasswordEncoder = bCryptPasswordEncoder;
        this.tokenService = tokenService;
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
    @Transactional
    public GetUserDto updateUserById(UpdateUserDto updateUserDto, int userId) {
        Optional<User> userOptional = userRepository.findById(userId);
        User user = userOptional.orElseThrow(() -> new EntityNotFoundException(String.format(USER_NOT_FOUND, userId)));

        user.setUsername(updateUserDto.getUsername());
        user.setPassword(bCryptPasswordEncoder.encode(updateUserDto.getPassword()));
        user.setRole(updateUserDto.getRole());
        user.setName(updateUserDto.getName());

        return mapFromEntityToDto(userRepository.save(user));
    }

    /**
     * {@inheritDoc}
     */
    @Override
    @Transactional
    public GetUserDto createUser(CreateUserDto createUserDto, Role role) {
        User user = mapFromDtoToEntity(createUserDto, role);
        user.setPassword(bCryptPasswordEncoder.encode(user.getPassword()));

        List<Device> devices = createUserDto.getDeviceDtoList().stream()
                .map(Mapper::mapFromDtoToEntity)
                .collect(Collectors.toList());

        for (Device device : devices) {
            device.setUser(user);
            deviceRepository.save(device);
        }

        return mapFromEntityToDto(userRepository.save(user));
    }

    /**
     * {@inheritDoc}
     */
    @Override
    @Transactional
    public void deleteUserById(int userId) {
        Optional<User> userOptional = userRepository.findById(userId);
        User user = userOptional.orElseThrow(() -> new EntityNotFoundException(String.format(USER_NOT_FOUND, userId)));

        List<Device> devices = user.getDeviceList();

        for (Device device : devices) {
            device.setUser(null);
        }

        userRepository.delete(user);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    @Transactional
    public void assignDeviceToUser(int userId, int deviceId) {
        Optional<User> userOptional = userRepository.findById(userId);
        User user = userOptional.orElseThrow(() -> new EntityNotFoundException(String.format(USER_NOT_FOUND, userId)));
        Optional<Device> deviceOptional = deviceRepository.findById(deviceId);
        Device device = deviceOptional.orElseThrow(() -> new EntityNotFoundException(String.format(DEVICE_WITH_ID_NOT_FOUND, deviceId)));

        device.setUser(user);
        deviceRepository.save(device);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    @Transactional
    public void unassignDeviceFromUser(int userId, int deviceId) {
        Optional<User> userOptional = userRepository.findById(userId);
        User user = userOptional.orElseThrow(() -> new EntityNotFoundException(String.format(USER_NOT_FOUND, userId)));
        Optional<Device> deviceOptional = deviceRepository.findById(deviceId);
        Device device = deviceOptional.orElseThrow(() -> new EntityNotFoundException(String.format(DEVICE_WITH_ID_NOT_FOUND, deviceId)));

        if (device.getUser() != null && Objects.equals(device.getUser().getId(), user.getId())) {
            device.setUser(null);
        }

        deviceRepository.save(device);
    }

    @Override
    public UserLoginResponse login(UserLoginRequest userLoginRequest) {
        Optional<User> userOptional = this.userRepository.findByUsername(userLoginRequest.getUsername());
        if (userOptional.isPresent()) {
            User user = userOptional.get();
            if (bCryptPasswordEncoder.matches(userLoginRequest.getPassword(), user.getPassword())) {
                return Mapper.mapFromModelToUserLoginResponse(user, tokenService.getJWTToken(user.getUsername(), user.getRole()));
            } else {
                throw new BadCredentialsException("Incorrect email/password!");
            }
        } else {
            throw new BadCredentialsException("Incorrect email/password!");
        }
    }

    @Override
    @Transactional
    public void assignUnassginedDeviceToUser(int userId, List<String> devices) {
        Optional<User> userOptional = userRepository.findById(userId);
        User user = userOptional.orElseThrow(() -> new EntityNotFoundException(String.format(USER_NOT_FOUND, userId)));
        devices.forEach(deviceId -> {
                        Optional<Device> device = deviceRepository.findById(Integer.parseInt(deviceId));
                    if (device.isPresent() && device.get().getUser() == null) {
                        device.get().setUser(user);
                        deviceRepository.save(device.get());
                    }
                }
        );
    }
}
