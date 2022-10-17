package com.onlineenergyutilityplatform.dto;

import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class UserLoginRequest {
    private String username;
    private String password;
}
