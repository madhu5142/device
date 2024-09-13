package com.example.device.contoller;

import com.example.device.controller.DeviceController;
import com.example.device.dto.DeviceCreateDTO;
import com.example.device.dto.DeviceDTO;
import com.example.device.dto.DeviceUpdateDTO;
import com.example.device.service.DeviceService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.util.Arrays;
import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(DeviceController.class)
public class DeviceControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private DeviceService deviceService;

    @Autowired
    private ObjectMapper objectMapper;

    private DeviceDTO deviceDTO;

    @BeforeEach
    public void setUp() {
        deviceDTO = new DeviceDTO();
        deviceDTO.setId(1L);
        deviceDTO.setName("Name");
        deviceDTO.setBrand("Brand");
    }

    @Test
    public void testAddDevice() throws Exception {
        DeviceCreateDTO deviceCreateDTO = new DeviceCreateDTO();
        deviceCreateDTO.setName("Name");
        deviceCreateDTO.setBrand("Brand");

        Mockito.when(deviceService.addDevice(any(DeviceCreateDTO.class))).thenReturn(deviceDTO);

        mockMvc.perform(post("/api/devices")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(deviceCreateDTO)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.id").value(deviceDTO.getId()))
                .andExpect(jsonPath("$.name").value(deviceDTO.getName()))
                .andExpect(jsonPath("$.brand").value(deviceDTO.getBrand()));
    }

    @Test
    public void testGetDevice() throws Exception {
        Mockito.when(deviceService.getDeviceById(1L)).thenReturn(deviceDTO);

        mockMvc.perform(get("/api/devices/1")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(deviceDTO.getId()))
                .andExpect(jsonPath("$.name").value(deviceDTO.getName()))
                .andExpect(jsonPath("$.brand").value(deviceDTO.getBrand()));
    }

    @Test
    public void testGetAllDevices() throws Exception {
        List<DeviceDTO> deviceList = Arrays.asList(deviceDTO);
        Mockito.when(deviceService.getAllDevices()).thenReturn(deviceList);

        mockMvc.perform(get("/api/devices")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.size()").value(deviceList.size()))
                .andExpect(jsonPath("$[0].id").value(deviceDTO.getId()))
                .andExpect(jsonPath("$[0].name").value(deviceDTO.getName()))
                .andExpect(jsonPath("$[0].brand").value(deviceDTO.getBrand()));
    }

    @Test
    public void testUpdateDevice() throws Exception {
    	DeviceUpdateDTO deviceUpdateDTO = new DeviceUpdateDTO();
    	deviceUpdateDTO.setName("New Name");
    	deviceUpdateDTO.setBrand("New Brand");
    	
        DeviceDTO updatedDeviceDTO = new DeviceDTO();
        updatedDeviceDTO.setId(1L);
        updatedDeviceDTO.setName("New Name");
        updatedDeviceDTO.setBrand("New Brand");

        Mockito.when(deviceService.updateDevice(eq(1L), any())).thenReturn(updatedDeviceDTO);

        mockMvc.perform(put("/api/devices/1")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(deviceUpdateDTO)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(updatedDeviceDTO.getId()))
                .andExpect(jsonPath("$.name").value(updatedDeviceDTO.getName()))
                .andExpect(jsonPath("$.brand").value(updatedDeviceDTO.getBrand()));
    }

    @Test
    public void testDeleteDevice() throws Exception {
        Mockito.doNothing().when(deviceService).deleteDevice(1L);

        mockMvc.perform(delete("/api/devices/1")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isNoContent());
    }
}

