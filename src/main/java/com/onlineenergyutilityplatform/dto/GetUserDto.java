package com.onlineenergyutilityplatform.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.onlineenergyutilityplatform.db.model.Role;
import lombok.*;

import java.util.List;

/**
 * Dto to display user data
 */
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class GetUserDto {

    private Integer id;
    private String name;
    private Role role;
    private String username;
    private String password;
    @JsonProperty("devices")
    private List<GetDeviceDto> deviceDtoList;

}
