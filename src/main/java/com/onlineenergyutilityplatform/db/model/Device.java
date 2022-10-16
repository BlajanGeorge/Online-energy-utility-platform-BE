package com.onlineenergyutilityplatform.db.model;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;

/**
 * Class to define Device db entity
 */
@Entity
@Table(name = "devices")
@Getter
@Setter
@NoArgsConstructor
public class Device {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    @Column(name = "description")
    private String description;
    @Column(name = "address", nullable = false)
    private String address;
    //expressed in kWh
    @Column(name = "max_hourly_energy_consumption")
    private Float maxHourlyEnergyConsumption;
    @Column
    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;

    public Device(String description, String address, Float maxHourlyEnergyConsumption, User user) {
        this.description = description;
        this.address = address;
        this.maxHourlyEnergyConsumption = maxHourlyEnergyConsumption;
        this.user = user;
    }
}
