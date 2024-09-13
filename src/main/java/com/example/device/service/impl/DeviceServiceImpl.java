package com.example.device.service.impl;

import com.example.device.dto.DeviceDTO;
import com.example.device.dto.DeviceCreateDTO;
import com.example.device.dto.DeviceUpdateDTO;
import com.example.device.exception.DeviceNotFoundException;
import com.example.device.mapper.DeviceMapper;
import com.example.device.entity.Device;
import com.example.device.repository.DeviceRepository;
import com.example.device.service.DeviceService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class DeviceServiceImpl implements DeviceService {

    @Autowired
    private DeviceRepository deviceRepository;

    @Autowired
    private DeviceMapper deviceMapper;

    public DeviceDTO addDevice(DeviceCreateDTO deviceCreateDTO) {
        Device device = deviceMapper.toDevice(deviceCreateDTO);
        return deviceMapper.toDeviceDTO(deviceRepository.save(device));
    }

    public DeviceDTO getDeviceById(Long id) {
        Device device = deviceRepository.findById(id)
                .orElseThrow(() -> new DeviceNotFoundException(id));
        return deviceMapper.toDeviceDTO(device);
    }

    public List<DeviceDTO> getAllDevices() {
        return deviceRepository.findAll().stream()
                .map(deviceMapper::toDeviceDTO)
                .collect(Collectors.toList());
    }

    public DeviceDTO updateDevice(Long id, DeviceUpdateDTO deviceUpdateDTO) {
        Device device = deviceRepository.findById(id)
                .orElseThrow(() -> new DeviceNotFoundException(id));
        deviceMapper.updateDeviceFromDTO(deviceUpdateDTO, device);
        return deviceMapper.toDeviceDTO(deviceRepository.save(device));
    }

    public void deleteDevice(Long id) {
        if (!deviceRepository.existsById(id)) {
            throw new DeviceNotFoundException(id);
        }
        deviceRepository.deleteById(id);
    }

    public List<DeviceDTO> searchDevicesByBrand(String brand) {
        return deviceRepository.findByBrand(brand).stream()
                .map(deviceMapper::toDeviceDTO)
                .collect(Collectors.toList());
    }
}
