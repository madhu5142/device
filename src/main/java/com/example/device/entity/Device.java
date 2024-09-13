package com.example.device.entity;

import jakarta.persistence.*;
import lombok.Data;
import java.time.LocalDateTime;

@Data
@Entity
@Table(name = "devices")
public class Device {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;
    
    private String brand;
    
    @Column(name = "created_at")
    private LocalDateTime createdAt = LocalDateTime.now();
}