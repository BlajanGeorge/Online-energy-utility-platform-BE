package com.onlineenergyutilityplatform.dto;

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
    private List<GetDeviceDto> deviceDtoList;

}
