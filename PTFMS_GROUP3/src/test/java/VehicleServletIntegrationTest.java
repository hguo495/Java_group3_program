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
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import transferobjects.CredentialsDTO;

import static org.junit.jupiter.api.Assertions.*;

import java.sql.SQLException;
import java.util.List;

public class VehicleServletIntegrationTest {

    private CredentialsDTO creds;

    @BeforeEach
    public void setup() {
        creds = new CredentialsDTO();
        creds.setUsername("CST8288");
        creds.setPassword("CST8288");

        DatabaseTestUtils.cleanTestData(creds); // 自动清理旧数据
    }

    @Test
    public void testVehicleRegistrationFlow() {
        try {
            // Setup test credentials
            CredentialsDTO creds = new CredentialsDTO();
            creds.setUsername("CST8288");
            creds.setPassword("CST8288");

            VehicleBusinessLogic vehicleLogic = new VehicleBusinessLogic(creds);

            // Use vehicle number "B999"
            String testNumber = "B999";

            // Clean up if already exists
            try {
                vehicleLogic.deleteVehicle(testNumber);
            } catch (Exception ignore) {}

            // Register new vehicle using method with 6 parameters
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
