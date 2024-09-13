package com.example.device.repository;

import com.example.device.entity.Device;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

public interface DeviceRepository extends JpaRepository<Device, Long> {
    List<Device> findByBrand(String brand);
}