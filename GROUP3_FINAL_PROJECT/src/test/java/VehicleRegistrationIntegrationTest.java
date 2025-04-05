/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */

/**
 * This class performs an end-to-end integration test for vehicle registration.
 * It verifies that a newly registered vehicle is correctly stored in the database.
 * 
 * @Author: Jinze Li
 * @modifiedby Mei
 */



import businesslayer.VehicleBusinessLogic;
import entity.Vehicle;
import org.junit.jupiter.api.Test;
import transferobjects.CredentialsDTO;

import java.sql.SQLException;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.BeforeEach;

public class VehicleRegistrationIntegrationTest {

    
    private CredentialsDTO creds;

    @BeforeEach
    public void setup() {
        creds = new CredentialsDTO();
        creds.setUsername("cst8288");
        creds.setPassword("cst8288");

        DatabaseTestUtils.cleanTestData(creds); // 自动清理旧数据
    }
    @Test
    public void testVehicleRegistrationEndToEnd() {
        try {
            CredentialsDTO creds = new CredentialsDTO();
            creds.setUsername("cst8288");
            creds.setPassword("cst8288");

            VehicleBusinessLogic vehicleLogic = new VehicleBusinessLogic(creds);

            // Use a unique vehicle number to avoid conflict
            String number = "TEST_" + System.currentTimeMillis();

            // Register new vehicle
            vehicleLogic.addVehicle("Diesel Bus", number, "Diesel", 0.55, 45, "Route Z");

            // Verify
            List<Vehicle> vehicles = vehicleLogic.getAllVehicles();
            boolean exists = vehicles.stream()
                    .anyMatch(v -> v.getNumber().equals(number) && v.getRoute().equals("Route Z"));

            assertTrue(exists, "Newly registered vehicle should exist in database");

        } catch (SQLException e) {
            fail("SQLException during registration: " + e.getMessage());
        }
    }
}
