package com.example.device.dto;

import lombok.Data;
import java.time.LocalDateTime;

@Data
public class DeviceDTO {
    private Long id;
    private String name;
    private String brand;
    private LocalDateTime createdAt;
}