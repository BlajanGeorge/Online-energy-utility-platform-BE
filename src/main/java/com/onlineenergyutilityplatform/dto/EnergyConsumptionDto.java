package com.onlineenergyutilityplatform.dto;

import lombok.*;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;

/**
 * Energy consumption dto
 */
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class EnergyConsumptionDto {
    @NotNull
    @Min(0)
    private Float energy;
    @NotNull
    private Long time;
}
