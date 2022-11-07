package com.onlineenergyutilityplatform.controller;


import com.onlineenergyutilityplatform.dto.DeviceDto;
import com.onlineenergyutilityplatform.dto.EnergyConsumptionDto;
import com.onlineenergyutilityplatform.dto.GetDeviceDto;
import com.onlineenergyutilityplatform.dto.PagedResult;
import com.onlineenergyutilityplatform.service.DeviceService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import javax.validation.constraints.Min;

import java.util.List;

import static com.onlineenergyutilityplatform.utilities.Constants.*;
import static org.springframework.http.ResponseEntity.ok;

/**
 * Device controller class
 */
@RestController
@Validated
@Slf4j
@CrossOrigin
public class DeviceController {
    /**
     * Service to handle user operations
     */
    private final DeviceService deviceService;

    public DeviceController(DeviceService deviceService) {
        this.deviceService = deviceService;
    }

    @GetMapping(DEVICES)
    public ResponseEntity<PagedResult<GetDeviceDto>> getDevices(final @RequestParam(name = PAGE_INDEX_QUERY_PARAM_NAME, required = false, defaultValue = "0") int pageIndex,
                                                                final @RequestParam(name = PAGE_SIZE_QUERY_PARAM_NAME, required = false, defaultValue = "1000") int pageSize) {
        log.info("Get devices request received with page index {}, page size {}.", pageIndex, pageSize);
        final PageRequest pageRequest = PageRequest.of(pageIndex, pageSize);

        PagedResult<GetDeviceDto> result = deviceService.getDevices(pageRequest);
        log.info("Returned devices with pageRequest {} as pageInfo {}",
                pageRequest,
                result.getPage());

        return ok(result);
    }

    @GetMapping(DEVICE_BY_ID)
    public ResponseEntity<GetDeviceDto> getDeviceById(@Min(1) @PathVariable(DEVICE_ID_PARAM) int id) {
        log.info("Get device by id request received for id {}", id);

        GetDeviceDto result = deviceService.getDeviceById(id);
        log.info("Returned device {}", result);

        return ok(result);
    }

    @PutMapping(DEVICE_BY_ID)
    public ResponseEntity<GetDeviceDto> updateDeviceById(@Min(1) @PathVariable(DEVICE_ID_PARAM) int id,
                                                         @Valid @RequestBody DeviceDto deviceDto) {
        log.info("Update device by id request received for id {}", id);

        GetDeviceDto result = deviceService.updateDeviceById(deviceDto, id);
        log.info("Returned device {}", result);

        return ok(result);
    }

    @DeleteMapping(DEVICE_BY_ID)
    public ResponseEntity<Void> deleteDeviceById(@Min(1) @PathVariable(DEVICE_ID_PARAM) int id) {
        log.info("Delete device by id request received for id {}", id);

        deviceService.deleteDeviceById(id);

        return ok().build();
    }

    @PostMapping(DEVICES)
    public ResponseEntity<GetDeviceDto> createDevice(@Valid @RequestBody DeviceDto deviceDto) {
        log.info("Create device request received with load {}", deviceDto);

        GetDeviceDto result = deviceService.createDevice(deviceDto);
        log.info("Returned device {}", result);

        return ok(result);
    }

    @PostMapping(DEVICE_BY_ID)
    public ResponseEntity<GetDeviceDto> addEnergyConsumptionReport(@Min(1) @PathVariable(DEVICE_ID_PARAM) int id,
                                                                   @Valid @RequestBody EnergyConsumptionDto energyConsumptionDto) {
        log.info("Add energy consumption report request received with load {} for device with id {}", energyConsumptionDto, id);

        GetDeviceDto result = deviceService.addEnergyConsumptionReport(energyConsumptionDto, id);
        log.info("Returned device {}", result);

        return ok(result);
    }

    @GetMapping(UNASSIGNED_DEVICES)
    public ResponseEntity<List<GetDeviceDto>> getUnassignedDevices() {
        log.info("Get unassigned devices request received.");

        return new ResponseEntity<>(deviceService.getUnassignedDevices(), HttpStatus.OK);
    }

    @GetMapping(ENERGY_REPORTS_FOR_DEVICE)
    public ResponseEntity<List<EnergyConsumptionDto>> getReportsForDevice(@Min(1) @PathVariable(DEVICE_ID_PARAM) int id,
                                                                          @RequestHeader("initialDate") Long initialDate,
                                                                          @RequestHeader("endDate") Long endDate) {
        log.info("Get reports for device with id {} request received.", id);

        List<EnergyConsumptionDto> result = deviceService.getReportsForDeviceInInterval(id, initialDate, endDate);

        return ok(result);
    }
}
