package com.example.device.mapper;

import com.example.device.dto.DeviceUpdateDTO;
import com.example.device.entity.Device;
import org.junit.jupiter.api.Test;
import org.mapstruct.factory.Mappers;

import static org.junit.jupiter.api.Assertions.*;

public class DeviceMapperTest {

    private final DeviceMapper deviceMapper = Mappers.getMapper(DeviceMapper.class);

    @Test
    public void testPartialUpdateDeviceWithNullOrEmptyFields() {
        DeviceUpdateDTO updateDTO = new DeviceUpdateDTO();
        updateDTO.setName("Updated Name");
        updateDTO.setBrand("");

        Device existingDevice = new Device();
        existingDevice.setName("Old Name");
        existingDevice.setBrand("Old Brand");

        deviceMapper.updateDeviceFromDTO(updateDTO, existingDevice);

        assertEquals("Updated Name", existingDevice.getName());
        assertEquals("Old Brand", existingDevice.getBrand());
    }
}

