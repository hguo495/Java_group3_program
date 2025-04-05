/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */


/**
 * This class tests the integration between component usage tracking
 * and the alert generation logic. It verifies that when a component exceeds
 * predefined usage and wear thresholds, a maintenance alert is triggered.
 * 
 * @Author: Jinze Li
 *  @modifiedby Mei
 */

import businesslayer.AlertBusinessLogic;
import businesslayer.ComponentBusinessLogic;
import businesslayer.VehicleBusinessLogic;
import entity.Alert;
import entity.Component;
import transferobjects.CredentialsDTO;

import org.junit.jupiter.api.Test;

import java.sql.SQLException;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.BeforeEach;

public class ComponentAlertIntegrationTest {
    private CredentialsDTO creds;

    @BeforeEach
    public void setup() {
        creds = new CredentialsDTO();
        creds.setUsername("cst8288");
        creds.setPassword("cst8288");

        DatabaseTestUtils.cleanTestData(creds); // 自动清理旧数据
    }
    @Test
    public void testComponentOverThresholdTriggersAlert() {
        try {
            CredentialsDTO creds = new CredentialsDTO();
            creds.setUsername("cst8288");
            creds.setPassword("cst8288");

            VehicleBusinessLogic vehicleLogic = new VehicleBusinessLogic(creds);
            ComponentBusinessLogic componentLogic = new ComponentBusinessLogic(creds);
            AlertBusinessLogic alertLogic = new AlertBusinessLogic(creds);

            // Create unique vehicle ID
            String uniqueVehicleId = "TEST_" + System.currentTimeMillis();

            // Add test vehicle
            vehicleLogic.addVehicle("Diesel Bus", uniqueVehicleId, "Diesel", 0.5, 50, "Test Route");

            // Add component over threshold
            Component component = new Component();
            component.setVehicleId(uniqueVehicleId);
            component.setType("Axle Bearing");
            component.setHoursUsed(11000);
            component.setWearPercentage(85);
            componentLogic.addComponent(component);

            Thread.sleep(200);

            // Check for triggered alert
            List<Alert> alerts = alertLogic.getAllAlerts();
            boolean exists = alerts.stream()
                    .anyMatch(a -> a.getType().equalsIgnoreCase("Maintenance") &&
                                   a.getVehicleId().equals(uniqueVehicleId) &&
                                   a.getMessage().toLowerCase().contains("needs maintenance"));

            assertTrue(exists, "Component threshold exceeded alert should be triggered");

        } catch (SQLException | InterruptedException e) {
            fail("Exception occurred: " + e.getMessage());
        }
    }
}
