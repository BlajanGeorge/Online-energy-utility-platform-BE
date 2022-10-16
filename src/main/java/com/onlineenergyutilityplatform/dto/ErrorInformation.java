package com.onlineenergyutilityplatform.dto;


import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * Dto to map error response
 */
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class ErrorInformation {
    private String message;
    private String type;
}
