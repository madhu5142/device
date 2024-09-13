package com.example.device.service;

import com.example.device.dto.DeviceCreateDTO;
import com.example.device.dto.DeviceDTO;
import com.example.device.dto.DeviceUpdateDTO;
import com.example.device.exception.DeviceNotFoundException;
import com.example.device.mapper.DeviceMapper;
import com.example.device.entity.Device;
import com.example.device.repository.DeviceRepository;
import com.example.device.service.impl.DeviceServiceImpl;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

public class DeviceServiceTest {
	
	@Mock
    private DeviceRepository deviceRepository;

    @Mock
    private DeviceMapper deviceMapper;

    @InjectMocks
    private DeviceServiceImpl deviceService;

    @BeforeEach
    public void setUp() {
        // Initialize mocks before each test
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void testAddDeviceSuccessfully() {

        DeviceCreateDTO deviceCreateDTO = new DeviceCreateDTO();
        deviceCreateDTO.setName("Device A");
        deviceCreateDTO.setBrand("Brand A");

        Device device = getDevice(deviceCreateDTO.getName(), deviceCreateDTO.getBrand());
        DeviceDTO deviceDTO = getDeviceDTO(1l, device.getName(), device.getBrand());

        when(deviceMapper.toDevice(deviceCreateDTO)).thenReturn(device);
        when(deviceRepository.save(any(Device.class))).thenReturn(device);
        when(deviceMapper.toDeviceDTO(device)).thenReturn(deviceDTO);

        DeviceDTO savedDevice = deviceService.addDevice(deviceCreateDTO);

        assertEquals("Device A", savedDevice.getName());
        assertEquals("Brand A", savedDevice.getBrand());
    }

    /*@Test
    public void testAddDeviceThrowsMissingFieldException() {

        DeviceCreateDTO deviceCreateDTO = new DeviceCreateDTO();
        deviceCreateDTO.setName(null);
        deviceCreateDTO.setBrand("Brand A");

        Exception exception = assertThrows(MethodArgumentNotValidException.class, () -> {
            deviceService.addDevice(deviceCreateDTO);
        });

        assertEquals("Device name is required", exception.getMessage());
    }*/

    @Test
    public void testGetDeviceByIdThrowsDeviceNotFoundException() {

        when(deviceRepository.findById(1L)).thenReturn(Optional.empty());

        Exception exception = assertThrows(DeviceNotFoundException.class, () -> {
            deviceService.getDeviceById(1L);
        });

        assertEquals("Device not found with id 1", exception.getMessage());
    }
    
    @Test
    public void testDeleteDeviceThrowsDeviceNotFoundException() {

        when(deviceRepository.existsById(1L)).thenReturn(false);

        Exception exception = assertThrows(DeviceNotFoundException.class, () -> {
            deviceService.deleteDevice(1L);
        });

        assertEquals("Device not found with id 1", exception.getMessage());
    }
    
    @Test
    public void testPartialUpdateDeviceSuccessfully() {

    	DeviceUpdateDTO updateDetailsDTO = new DeviceUpdateDTO();
        updateDetailsDTO.setName("New Name");

        Device existingDevice = new Device();
        existingDevice.setId(1L);
        existingDevice.setName("Old Name");
        existingDevice.setBrand("Old Brand");

        when(deviceRepository.findById(1L)).thenReturn(Optional.of(existingDevice));
        doNothing().when(deviceMapper).updateDeviceFromDTO(updateDetailsDTO, existingDevice);
        when(deviceRepository.save(any(Device.class))).thenReturn(existingDevice);
        DeviceDTO deviceDTO = getDeviceDTO(1L, updateDetailsDTO.getName(), existingDevice.getBrand());
        when(deviceMapper.toDeviceDTO(existingDevice)).thenReturn(deviceDTO);

        DeviceDTO updatedDeviceDTO = deviceService.updateDevice(1L, updateDetailsDTO);

        assertEquals("New Name", updatedDeviceDTO.getName());
        assertEquals("Old Brand", updatedDeviceDTO.getBrand());
    }
    
    public Device getDevice(String name, String brand) {
    	Device device = new Device();
        device.setName(name);
        device.setBrand(brand);
        return device;
    }
    
    public DeviceDTO getDeviceDTO(Long id, String name, String brand) {
    	DeviceDTO deviceDTO = new DeviceDTO();
    	deviceDTO.setId(id);
    	deviceDTO.setName(name);
    	deviceDTO.setBrand(brand);
    	return deviceDTO;
    }
}
