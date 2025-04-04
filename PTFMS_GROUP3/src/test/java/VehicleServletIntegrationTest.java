/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */

/**
 * This class tests the vehicle registration flow from the servlet layer
 * to the business logic and database layers.
 * 
 * Author: Jinze Li
 */

import businesslayer.VehicleBusinessLogic;
import entity.Vehicle;
import org.junit.jupiter.api.Test;
import transferobjects.CredentialsDTO;

import static org.junit.jupiter.api.Assertions.*;

import java.sql.SQLException;
import java.util.List;
import businesslayer.VehicleBuilder;

public class VehicleServletIntegrationTest {

    @Test
    public void testVehicleRegistrationFlow() {
        try {
            // Setup test credentials
            CredentialsDTO creds = new CredentialsDTO();
            creds.setUsername("CST8288");
            creds.setPassword("CST8288");

            VehicleBusinessLogic vehicleLogic = new VehicleBusinessLogic(creds);

            // Clean up if vehicle already exists
            vehicleLogic.deleteVehicle("B999");
            String testNumber = "B999";
            String testId = "TEST_BUS_999";

            try {
                vehicleLogic.deleteVehicle(testId);
            } catch (Exception ignore) {}

            // Register new vehicle
            vehicleLogic.addVehicle("Diesel Bus", testNumber, "Diesel", 0.5, 60, "Test Route");

            // Retrieve all vehicles and verify the test vehicle is present
            List<Vehicle> vehicles = vehicleLogic.getAllVehicles();
            Vehicle found = vehicles.stream()
                .filter(v -> testNumber.equals(v.getNumber()))
                .findFirst()
                .orElse(null);

            assertNotNull(found, "Vehicle should exist in the database");
            assertEquals("Diesel", found.getFuelType());
            assertEquals("Test Route", found.getRoute());

        } catch (SQLException e) {
            fail("SQLException occurred: " + e.getMessage());
        }
    }
}
