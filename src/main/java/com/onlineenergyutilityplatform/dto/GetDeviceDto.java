package com.onlineenergyutilityplatform.dto;


import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;

import java.util.List;

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
    @JsonProperty("energyConsumption")
    private List<EnergyConsumptionDto> energyConsumptionDtoList;
}
