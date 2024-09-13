package com.example.device.service;

import com.example.device.dto.DeviceDTO;
import com.example.device.dto.DeviceCreateDTO;
import com.example.device.dto.DeviceUpdateDTO;
import java.util.List;

public interface DeviceService {

    public DeviceDTO addDevice(DeviceCreateDTO deviceCreateDTO);

    public DeviceDTO getDeviceById(Long id);

    public List<DeviceDTO> getAllDevices();

    public DeviceDTO updateDevice(Long id, DeviceUpdateDTO deviceUpdateDTO);

    public void deleteDevice(Long id);

    public List<DeviceDTO> searchDevicesByBrand(String brand);
}
