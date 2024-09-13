package com.example.device.controller;

import com.example.device.dto.DeviceDTO;
import com.example.device.dto.DeviceCreateDTO;
import com.example.device.dto.DeviceUpdateDTO;
import com.example.device.service.DeviceService;
import jakarta.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/devices")
public class DeviceController {

    @Autowired
    private DeviceService deviceService;

    @PostMapping
    public ResponseEntity<DeviceDTO> addDevice(@Valid @RequestBody DeviceCreateDTO deviceCreateDTO) {
    	DeviceDTO deviceDTO = deviceService.addDevice(deviceCreateDTO);
        return new ResponseEntity<>(deviceDTO, HttpStatus.CREATED);
    }

    @GetMapping("/{id}")
    public ResponseEntity<DeviceDTO> getDeviceById(@PathVariable Long id) {
        return ResponseEntity.ok(deviceService.getDeviceById(id));
    }

    @GetMapping
    public ResponseEntity<List<DeviceDTO>> getAllDevices() {
    	List<DeviceDTO> devices  = deviceService.getAllDevices();
        return new ResponseEntity<>(devices, HttpStatus.OK);
    }

    @PutMapping("/{id}")
    public ResponseEntity<DeviceDTO> updateDevice(@PathVariable Long id, @RequestBody DeviceUpdateDTO deviceUpdateDTO) {
        return ResponseEntity.ok(deviceService.updateDevice(id, deviceUpdateDTO));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteDevice(@PathVariable Long id) {
        deviceService.deleteDevice(id);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/search")
    public ResponseEntity<List<DeviceDTO>> searchDevicesByBrand(@RequestParam String brand) {
    	List<DeviceDTO> devices  = deviceService.searchDevicesByBrand(brand);
        return new ResponseEntity<>(devices, HttpStatus.OK);
    }
}
