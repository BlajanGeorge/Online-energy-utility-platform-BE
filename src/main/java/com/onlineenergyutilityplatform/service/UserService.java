package com.onlineenergyutilityplatform.service;

import com.onlineenergyutilityplatform.db.model.Role;
import com.onlineenergyutilityplatform.dto.*;
import org.springframework.data.domain.PageRequest;

/**
 * User Service class to handle user operations
 */
public interface UserService {

    /**
     * Return a paginated request of users
     * @return {@link PagedResult}
     */
    PagedResult<GetUserDto> getUsers(PageRequest pageRequest);

    /**
     * Return user by id if exist
     *
     * @param userId user identifier
     * @return {@link GetUserDto}
     */
    GetUserDto getUserById(int userId);

    /**
     * Update user by id if exist
     *
     * @param userId user identifier
     * @return {@link GetUserDto}
     */
    GetUserDto updateUserById(UpdateUserDto updateUserDto, int userId);

    /**
     * Create user
     *
     * @param createUserDto create user request dto
     * @return {@link GetUserDto}
     */
    GetUserDto createUser(CreateUserDto createUserDto, Role role);

    /**
     * Delete user by id if exist
     *
     * @param userId user identifier
     */
    void deleteUserById(int userId);

    /**
     * Assign device to user
     * @param userId user identifier
     * @param deviceId device identifier
     */
    void assignDeviceToUser(int userId, int deviceId);

    UserLoginResponse login(UserLoginRequest userLoginRequest);
}
