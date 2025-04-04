/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */

/**
 * This class tests the integration between component usage tracking
 * and the alert generation logic. It verifies that when a component exceeds
 * predefined usage and wear thresholds, a maintenance alert is triggered.
 * 
 * Author: Jinze Li
 */

import businesslayer.AlertBusinessLogic;
import businesslayer.ComponentBusinessLogic;
import entity.Alert;
import entity.Component;
import transferobjects.CredentialsDTO;

import org.junit.jupiter.api.Test;

import java.sql.SQLException;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

public class ComponentAlertIntegrationTest {

    @Test
    public void testComponentOverThresholdTriggersAlert() {
        try {
            CredentialsDTO creds = new CredentialsDTO();
            creds.setUsername("CST8288");
            creds.setPassword("CST8288");

            ComponentBusinessLogic componentLogic = new ComponentBusinessLogic(creds);
            AlertBusinessLogic alertLogic = new AlertBusinessLogic(creds);

            // Create a component that exceeds lifespan and wear thresholds
            Component component = new Component();
            component.setVehicleId("BUS001");
            component.setType("Axle Bearing");
            component.setHoursUsed(11000);  // Over 10,000 hours
            component.setWearPercentage(85); // Over 80%

            componentLogic.addComponent(component);

            // Check if a maintenance alert has been triggered
            List<Alert> alerts = alertLogic.getAllAlerts();
            boolean exists = alerts.stream()
                    .anyMatch(a -> a.getType().equals("Maintenance") &&
                                   a.getVehicleId().equals("BUS001") &&
                                   a.getMessage().toLowerCase().contains("exceeded"));

            assertTrue(exists, "Component threshold exceeded alert should be triggered");

        } catch (SQLException e) {
            fail("SQLException occurred: " + e.getMessage());
        }
    }
}
