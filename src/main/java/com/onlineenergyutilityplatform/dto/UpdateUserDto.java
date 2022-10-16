package com.onlineenergyutilityplatform.dto;

import com.onlineenergyutilityplatform.db.model.Role;
import com.onlineenergyutilityplatform.utilities.PasswordConstraint;
import com.onlineenergyutilityplatform.utilities.UsernameConstraint;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.NotBlank;

/**
 * Dto for update user request
 */
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class UpdateUserDto {

    @NotBlank
    @Length(max = 100)
    private String name;
    private Role role;
    @UsernameConstraint
    private String username;
    @PasswordConstraint
    private String password;
}
