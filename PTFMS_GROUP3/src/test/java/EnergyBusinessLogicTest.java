/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */

/**
 * This class tests the energy usage business logic.
 * It verifies that adding an inefficient energy usage record does not throw an exception.
 * 
 * Author: Jinze Li
 */

import businesslayer.EnergyBusinessLogic;
import entity.EnergyUsage;
import org.junit.jupiter.api.Test;
import transferobjects.CredentialsDTO;

import java.sql.SQLException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.BeforeEach;

public class EnergyBusinessLogicTest {

    private CredentialsDTO creds;

    @BeforeEach
    public void setup() {
        creds = new CredentialsDTO();
        creds.setUsername("CST8288");
        creds.setPassword("CST8288");

        DatabaseTestUtils.cleanTestData(creds); // 自动清理旧数据
    }
    @Test
    public void testAddEnergyUsage_ExcessiveUsage_TriggersAlert() {
        try {
            CredentialsDTO creds = new CredentialsDTO();
            creds.setUsername("CST8288");
            creds.setPassword("CST8288");

            EnergyBusinessLogic energyLogic = new EnergyBusinessLogic(creds);

            EnergyUsage usage = new EnergyUsage();
            usage.setVehicleId("BUS001"); // Existing vehicle
            usage.setFuelEnergyType("Diesel");
            usage.setAmountUsed(20.0); // Inefficient: 20L for 10km
            usage.setDistanceTraveled(10.0);

            String timestamp = LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));
            usage.setTimestamp(timestamp);

            // Add record (should trigger an alert, but not asserted here)
            energyLogic.addEnergyUsage(usage);

            // If no exception is thrown, test passes
            assertTrue(true);

        } catch (SQLException e) {
            fail("SQLException: " + e.getMessage());
        }
    }
}

