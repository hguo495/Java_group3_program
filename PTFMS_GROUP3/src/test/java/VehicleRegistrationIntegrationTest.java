/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */

/**
 * This class performs an end-to-end integration test for vehicle registration.
 * It verifies that a newly registered vehicle is correctly stored in the database.
 * 
 * Author: Jinze Li
 */

import businesslayer.VehicleBusinessLogic;
import entity.Vehicle;
import org.junit.jupiter.api.Test;
import transferobjects.CredentialsDTO;

import java.sql.SQLException;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

public class VehicleRegistrationIntegrationTest {

    @Test
    public void testVehicleRegistrationEndToEnd() {
        try {
            CredentialsDTO creds = new CredentialsDTO();
            creds.setUsername("CST8288");
            creds.setPassword("CST8288");

            VehicleBusinessLogic vehicleLogic = new VehicleBusinessLogic(creds);

            // Define vehicle data to be registered
            String type = "Diesel Bus";
            String number = "B999";
            String fuelType = "Diesel";
            double consumptionRate = 0.55;
            int maxPassengers = 45;
            String route = "Route Z";

            // Register vehicle
            vehicleLogic.addVehicle(type, number, fuelType, consumptionRate, maxPassengers, route);

            // Retrieve all vehicles and check if the new one exists
            List<Vehicle> vehicles = vehicleLogic.getAllVehicles();
            boolean exists = vehicles.stream()
                    .anyMatch(v -> v.getNumber().equals("B999") && v.getRoute().equals("Route Z"));

            assertTrue(exists, "Newly registered vehicle should exist in database");

        } catch (SQLException e) {
            fail("SQLException during registration: " + e.getMessage());
        }
    }
}
