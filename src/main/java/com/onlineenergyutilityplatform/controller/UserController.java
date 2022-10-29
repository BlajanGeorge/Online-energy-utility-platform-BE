package com.onlineenergyutilityplatform.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.onlineenergyutilityplatform.db.model.Role;
import com.onlineenergyutilityplatform.db.model.User;
import com.onlineenergyutilityplatform.db.repositories.UserRepository;
import com.onlineenergyutilityplatform.dto.*;
import com.onlineenergyutilityplatform.exceptions.ForbiddenAccess;
import com.onlineenergyutilityplatform.service.UserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.persistence.EntityNotFoundException;
import javax.validation.Valid;
import javax.validation.constraints.Min;
import java.util.*;

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
    private final UserRepository userRepository;

    public UserController(UserService userService,
                          UserRepository userRepository) {
        this.userService = userService;
        this.userRepository = userRepository;
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
    public ResponseEntity<GetUserDto> getUserById(@Min(1) @PathVariable("id") int id,
                                                  @RequestHeader("authorization") String authorization) throws JsonProcessingException {
        check(authorization, id);
        log.info("Get user by id request received for id {}", id);

        GetUserDto result = userService.getUserById(id);
        log.info("Returned user {}", result);

        return ok(result);
    }

    public void check(String authorization, int id) throws JsonProcessingException {
        String[] chunks = authorization.split("\\.");
        Base64.Decoder decoder = Base64.getUrlDecoder();

        String payload = new String(decoder.decode(chunks[1]));
        HashMap mapping = new ObjectMapper().readValue(payload, HashMap.class);
        final String username = (String) mapping.get("sub");
        final String authority = (String) ((List) mapping.get("authorities")).get(0);
        if (authority.equals("ROLE_CLIENT")) {
            Optional<User> userOptional = userRepository.findByUsername(username);
            User user = userOptional.orElseThrow(() -> new EntityNotFoundException(String.format(USER_NOT_FOUND, username)));

            if (user.getId() != id) {
                throw new ForbiddenAccess("Forbidden access");
            }
        }
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
