//package com.onlineenergyutilityplatform.kafka;
//
//import com.fasterxml.jackson.databind.ObjectMapper;
//import com.onlineenergyutilityplatform.db.model.Device;
//import com.onlineenergyutilityplatform.db.model.EnergyConsumption;
//import com.onlineenergyutilityplatform.db.repositories.DeviceRepository;
//import com.onlineenergyutilityplatform.db.repositories.EnergyConsumptionRepository;
//import org.springframework.kafka.annotation.KafkaListener;
//import org.springframework.messaging.simp.SimpMessagingTemplate;
//import org.springframework.stereotype.Component;
//
//import java.io.IOException;
//import java.util.Map;
//import java.util.Optional;
//
//@Component
//public class KafkaConsumer {
//
//    private final ObjectMapper objectMapper = new ObjectMapper();
//    private final EnergyConsumptionRepository energyConsumptionRepository;
//    private final DeviceRepository deviceRepository;
//    private final SimpMessagingTemplate template;
//
//    public KafkaConsumer(EnergyConsumptionRepository energyConsumptionRepository, DeviceRepository deviceRepository, SimpMessagingTemplate simpMessagingTemplate) {
//        this.energyConsumptionRepository = energyConsumptionRepository;
//        this.deviceRepository = deviceRepository;
//        this.template = simpMessagingTemplate;
//    }
//
//    @KafkaListener(topics = "records-topic", groupId = "1")
//    public void kafkaListener(String message) throws IOException {
//        Map event = objectMapper.readValue(message, Map.class);
//        EnergyConsumption energyConsumption = new EnergyConsumption(Float.valueOf((String) event.get("energy")), Long.valueOf((String) event.get("time")));
//        Optional<Device> deviceOptional = deviceRepository.findById(Integer.valueOf((String) event.get("device")));
//        if (deviceOptional.isPresent()) {
//            Device device = deviceOptional.get();
//            energyConsumption.setDevice(device);
//            energyConsumptionRepository.saveAndFlush(energyConsumption);
//            Float max = device.getMaxHourlyEnergyConsumption();
//            if (max != null && energyConsumption.getEnergy() > max && device.getUser() != null) {
//                template.convertAndSend("/serverPublish/messageOnClient/" + device.getUser().getId().toString(),
//                        String.format("Consumption %s exceeded max hourly consumption %s for device id %s", energyConsumption.getEnergy(), max, device.getId()));
//            }
//        }
//
//    }
//}
