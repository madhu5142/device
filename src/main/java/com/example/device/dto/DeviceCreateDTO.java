package com.example.device.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class DeviceCreateDTO {

    @NotBlank(message = "Device name is required")
    private String name;

    @NotBlank(message = "Device brand is required")
    private String brand;
}