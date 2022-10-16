package com.onlineenergyutilityplatform.dto;


import lombok.*;

/**
 * Dto to display device data
 */
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class GetDeviceDto {
    private Integer id;
    private String description;
    private String address;
    private Float maxHourlyEnergyConsumption;
    private Integer userId;
}
