package com.onlineenergyutilityplatform.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class UserLoginResponse {
    private int id;
    private String token;
    private boolean isAdmin;
    private String username;
}
