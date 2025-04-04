/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */

/**
 * This class tests the alert triggering logic for components that exceed
 * wear or usage thresholds. It ensures that a maintenance alert is generated
 * when expected conditions are met.
 * 
 * Author: Jinze Li
 */

import businesslayer.AlertBusinessLogic;
import businesslayer.ComponentBusinessLogic;
import entity.Alert;
import entity.Component;
import org.junit.jupiter.api.Test;
import transferobjects.CredentialsDTO;

import java.sql.SQLException;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

public class ComponentAlertTest {

    @Test
    public void testAlertTriggeredForHighUsageComponent() {
        try {
            CredentialsDTO creds = new CredentialsDTO();
            creds.setUsername("CST8288");
            creds.setPassword("CST8288");

            ComponentBusinessLogic componentLogic = new ComponentBusinessLogic(creds);
            AlertBusinessLogic alertLogic = new AlertBusinessLogic(creds);

            // Create a component that exceeds the usage threshold
            Component component = new Component();
            component.setVehicleId("BUS001");
            component.setType("Brakes");
            component.setHoursUsed(90000); // Exceeds 10-year threshold
            component.setWearPercentage(85); // High wear
            component.setDiagnosticStatus("Warning");

            componentLogic.addComponent(component);

            // Check if a maintenance alert was triggered for this component
            List<Alert> alerts = alertLogic.getFilteredAlerts("Maintenance", "Pending", "BUS001");

            boolean found = alerts.stream()
                    .anyMatch(a -> a.getMessage().toLowerCase().contains("maintenance required"));

            assertTrue(found, "Alert should be triggered for component exceeding wear/usage threshold");

        } catch (SQLException e) {
            fail("SQLException: " + e.getMessage());
        }
    }
}
