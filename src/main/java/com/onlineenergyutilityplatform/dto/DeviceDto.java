package com.onlineenergyutilityplatform.dto;

import lombok.*;

import javax.validation.constraints.NotBlank;

/**
 * Dto for create device request
 */
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class DeviceDto {
    private String description;
    @NotBlank
    private String address;
    private Float maxHourlyEnergyConsumption;
}
