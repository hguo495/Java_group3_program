/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */

/**
 * This class tests the integration between energy usage logging and the alert system.
 * It verifies that submitting a low-efficiency energy usage record triggers an energy alert.
 * 
 * Author: Jinze Li
 */

import businesslayer.AlertBusinessLogic;
import businesslayer.EnergyBusinessLogic;
import entity.Alert;
import entity.EnergyUsage;
import transferobjects.CredentialsDTO;

import org.junit.jupiter.api.Test;

import java.sql.SQLException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

public class EnergyUsageIntegrationTest {

    @Test
    public void testHighEnergyUsageTriggersAlert() {
        try {
            CredentialsDTO creds = new CredentialsDTO();
            creds.setUsername("CST8288");
            creds.setPassword("CST8288");

            EnergyBusinessLogic energyLogic = new EnergyBusinessLogic(creds);
            AlertBusinessLogic alertLogic = new AlertBusinessLogic(creds);

            // Submit an energy usage record with low fuel efficiency
            EnergyUsage usage = new EnergyUsage();
            usage.setVehicleId("BUS001");
            usage.setFuelEnergyType("Diesel");
            usage.setAmountUsed(20.0); // Inefficient usage: 20 / 50 = 2.5 mpg < 5.0
            usage.setDistanceTraveled(50.0);

            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
            String timestamp = LocalDateTime.now().format(formatter);
            usage.setTimestamp(timestamp);

            // Add the energy usage record
            energyLogic.addEnergyUsage(usage);

            // Check if an energy alert has been triggered
            List<Alert> alerts = alertLogic.getAllAlerts();
            boolean exists = alerts.stream()
                    .anyMatch(a -> a.getType().equals("Energy") &&
                                   a.getVehicleId().equals("BUS001") &&
                                   a.getMessage().toLowerCase().contains("excessive"));

            assertTrue(exists, "Alert for excessive energy usage should be triggered");

        } catch (SQLException e) {
            fail("SQLException occurred: " + e.getMessage());
        }
    }
}
