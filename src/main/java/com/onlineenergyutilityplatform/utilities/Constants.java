package com.onlineenergyutilityplatform.utilities;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;

/**
 * Constants class
 */
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class Constants {
    public static final String USER_NOT_FOUND = "User with id %s not found.";
    public static final String DEVICE_WITH_ID_NOT_FOUND = "Device with id %s not found.";
    public static final String USERS = "/users";
    public static final String USERS_CREATE_CLIENT = USERS + "/client";
    public static final String DEVICES = "/devices";
    public static final String UNASSIGNED_DEVICES = "/devices_unassgined";
    public static final String USERS_CREATE_ADMIN = USERS + "/admin";
    public static final String ID_PARAM = "id";
    public static final String DEVICE_ID_PARAM = "deviceId";
    public static final String USER_BY_ID = USERS + "/{" + ID_PARAM + "}";
    public static final String DEVICE_TO_USER = USER_BY_ID + "/{" + DEVICE_ID_PARAM + "}";
    public static final String UNNASIGNED_DEVICE_TO_USER = USER_BY_ID + "/unassigned";
    public static final String DEVICE_BY_ID = DEVICES + "/{" + DEVICE_ID_PARAM + "}";
    public static final String ENERGY_REPORTS_FOR_DEVICE = DEVICE_BY_ID + "/energy";
    public static final String PAGE_INDEX_QUERY_PARAM_NAME = "page";
    public static final String PAGE_SIZE_QUERY_PARAM_NAME = "size";
    public static final String SECRET = "secret";
}
