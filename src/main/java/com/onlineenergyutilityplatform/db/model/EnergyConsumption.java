package com.onlineenergyutilityplatform.db.model;


import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.sql.Timestamp;

/**
 * Class to define Energy db entity
 */
@Entity
@Table(name = "energy_consumption")
@Getter
@Setter
@NoArgsConstructor
public class EnergyConsumption {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    //expressed in kWh
    @Column(name = "energy")
    private Float energy;
    @Column(name = "time")
    private Timestamp time;
    @ManyToOne
    @JoinColumn(name = "device_id")
    private Device device;

    public EnergyConsumption(Float energy, Timestamp time) {
        this.energy = energy;
        this.time = time;
    }
}
