package com.onlineenergyutilityplatform.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.onlineenergyutilityplatform.utilities.PasswordConstraint;
import com.onlineenergyutilityplatform.utilities.UsernameConstraint;
import lombok.*;
import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.util.List;

/**
 * Dto for create user request
 */
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class CreateUserDto {
    @NotBlank
    @Length(max = 100)
    private String name;
    @NotBlank
    @UsernameConstraint
    private String username;
    @NotBlank
    @PasswordConstraint
    private String password;
    @NotNull
    @JsonProperty("devices")
    private List<DeviceDto> deviceDtoList;
}
