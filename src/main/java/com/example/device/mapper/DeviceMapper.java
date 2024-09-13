package com.example.device.mapper;

import com.example.device.dto.DeviceDTO;
import com.example.device.dto.DeviceCreateDTO;
import com.example.device.dto.DeviceUpdateDTO;
import com.example.device.entity.Device;
import org.mapstruct.BeanMapping;
import org.mapstruct.Condition;
import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;
import org.mapstruct.NullValuePropertyMappingStrategy;

@Mapper(componentModel = "spring")
public interface DeviceMapper {

    DeviceDTO toDeviceDTO(Device device);

    Device toDevice(DeviceCreateDTO deviceCreateDTO);

    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    void updateDeviceFromDTO(DeviceUpdateDTO deviceUpdateDTO, @MappingTarget Device device);
    
    @Condition
    default boolean isNotNullOrEmpty(String value) {
        return value != null && !value.trim().isEmpty();
    }
}