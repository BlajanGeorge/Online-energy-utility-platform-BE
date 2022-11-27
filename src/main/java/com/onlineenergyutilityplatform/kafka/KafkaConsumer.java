package com.onlineenergyutilityplatform.kafka;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.onlineenergyutilityplatform.db.model.EnergyConsumption;
import com.onlineenergyutilityplatform.db.repositories.DeviceRepository;
import com.onlineenergyutilityplatform.db.repositories.EnergyConsumptionRepository;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

import java.util.Map;

@Component
public class KafkaConsumer {

    private final ObjectMapper objectMapper = new ObjectMapper();
    private final EnergyConsumptionRepository energyConsumptionRepository;
    private final DeviceRepository deviceRepository;

    public KafkaConsumer(EnergyConsumptionRepository energyConsumptionRepository, DeviceRepository deviceRepository) {
        this.energyConsumptionRepository = energyConsumptionRepository;
        this.deviceRepository = deviceRepository;
    }

    @KafkaListener(topics = "records-topic", groupId = "1")
    public void kafkaListener(String message) throws JsonProcessingException {
        Map event = objectMapper.readValue(message, Map.class);
        EnergyConsumption energyConsumption = new EnergyConsumption(Float.valueOf((String) event.get("energy")), Long.valueOf((String) event.get("time")));
        energyConsumption.setDevice(deviceRepository.findById(Integer.valueOf((String) event.get("device"))).get());
        energyConsumptionRepository.saveAndFlush(energyConsumption);
    }
}
