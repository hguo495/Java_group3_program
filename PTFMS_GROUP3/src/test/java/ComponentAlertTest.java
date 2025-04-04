/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */

/**
 * This class tests the alert triggering logic for components that exceed
 * wear or usage thresholds. It ensures that a maintenance alert is generated
 * when expected conditions are met.
 * 
 * @Author: Jinze Li
 * @modifiedby Mei
 */

import businesslayer.AlertBusinessLogic;
import businesslayer.ComponentBusinessLogic;
import businesslayer.VehicleBusinessLogic;
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

            VehicleBusinessLogic vehicleLogic = new VehicleBusinessLogic(creds);
            ComponentBusinessLogic componentLogic = new ComponentBusinessLogic(creds);
            AlertBusinessLogic alertLogic = new AlertBusinessLogic(creds);

            // Create unique vehicle ID
            String uniqueVehicleId = "TEST_" + System.currentTimeMillis();

            // Add test vehicle
            vehicleLogic.addVehicle("Diesel Bus", uniqueVehicleId, "Diesel", 0.5, 50, "Test Route");

            // Add component that should trigger alert
            Component component = new Component();
            component.setVehicleId(uniqueVehicleId);
            component.setType("Brakes");
            component.setHoursUsed(90000);
            component.setWearPercentage(85);
            component.setDiagnosticStatus("Warning");
            componentLogic.addComponent(component);

            Thread.sleep(200);

            List<Alert> alerts = alertLogic.getFilteredAlerts("Maintenance", "Pending", uniqueVehicleId);
            boolean found = alerts.stream()
                    .anyMatch(a -> a.getMessage().toLowerCase().contains("needs maintenance"));

            assertTrue(found, "Alert should be triggered for component exceeding wear/usage threshold");

        } catch (SQLException | InterruptedException e) {
            fail("Exception occurred: " + e.getMessage());
        }
    }
}
