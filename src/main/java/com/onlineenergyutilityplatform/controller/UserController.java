package com.onlineenergyutilityplatform.controller;

import com.onlineenergyutilityplatform.db.model.Role;
import com.onlineenergyutilityplatform.dto.*;
import com.onlineenergyutilityplatform.service.UserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import javax.validation.constraints.Min;

import static com.onlineenergyutilityplatform.utilities.Constants.*;
import static org.springframework.http.ResponseEntity.ok;

/**
 * User controller class
 */
@RestController
@Validated
@Slf4j
@CrossOrigin
public class UserController {
    /**
     * Service to handle user operations
     */
    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping(USERS)
    public ResponseEntity<PagedResult<GetUserDto>> getUsers(final @RequestParam(name = PAGE_INDEX_QUERY_PARAM_NAME, required = false, defaultValue = "0") int pageIndex,
                                                            final @RequestParam(name = PAGE_SIZE_QUERY_PARAM_NAME, required = false, defaultValue = "20") int pageSize) {
        log.info("Get users request received with page index {}, page size {}.", pageIndex, pageSize);
        final PageRequest pageRequest = PageRequest.of(pageIndex, pageSize);

        PagedResult<GetUserDto> result = userService.getUsers(pageRequest);
        log.info("Returned users with pageRequest {} as pageInfo {}",
                pageRequest,
                result.getPage());

        return ok(result);
    }

    @GetMapping(USER_BY_ID)
    public ResponseEntity<GetUserDto> getUserById(@Min(1) @PathVariable("id") int id) {
        log.info("Get user by id request received for id {}", id);

        GetUserDto result = userService.getUserById(id);
        log.info("Returned user {}", result);

        return ok(result);
    }

    @PutMapping(USER_BY_ID)
    public ResponseEntity<GetUserDto> updateUserById(@Min(1) @PathVariable("id") int id,
                                                     @Valid @RequestBody UpdateUserDto updateUserDto) {
        log.info("Update user by id request received for id {}", id);

        GetUserDto result = userService.updateUserById(updateUserDto, id);
        log.info("Returned user {}", result);

        return ok(result);
    }

    @DeleteMapping(USER_BY_ID)
    public ResponseEntity<Void> deleteUserById(@Min(1) @PathVariable("id") int id) {
        log.info("Delete user by id request received for id {}", id);

        userService.deleteUserById(id);

        return ok().build();
    }

    @PostMapping("/login")
    public ResponseEntity<UserLoginResponse> login(@RequestBody UserLoginRequest userLoginRequest) {
        log.info("Login request received for data {}.", userLoginRequest);

        UserLoginResponse userLoginResponse = userService.login(userLoginRequest);

        return ok(userLoginResponse);
    }

    @PostMapping(USERS_CREATE_CLIENT)
    public ResponseEntity<GetUserDto> createClient(@Valid @RequestBody CreateUserDto createUserDto) {
        log.info("Create user request received with load {}", createUserDto);

        GetUserDto result = userService.createUser(createUserDto, Role.CLIENT);
        log.info("Returned user {}", result);

        return ok(result);
    }

    @PostMapping(USERS_CREATE_ADMIN)
    public ResponseEntity<GetUserDto> createAdmin(@Valid @RequestBody CreateUserDto createUserDto) {
        log.info("Create user request received with load {}", createUserDto);

        GetUserDto result = userService.createUser(createUserDto, Role.ADMINISTRATOR);
        log.info("Returned user {}", result);

        return ok(result);
    }

    @PutMapping(DEVICE_TO_USER)
    public ResponseEntity<Void> assignDeviceToUser(@Min(1) @PathVariable("id") int id,
                                                   @Min(1) @PathVariable("deviceId") int deviceId) {
        log.info("Assign device on user request received for user id {} and device id {}.", id, deviceId);

        userService.assignDeviceToUser(id, deviceId);

        return ok().build();
    }

}
