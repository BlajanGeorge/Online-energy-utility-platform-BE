package com.onlineenergyutilityplatform.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Message {
    private String type;
    private String fromUserId;
    private String toUserId;
    private String message;
    private String username;
}
