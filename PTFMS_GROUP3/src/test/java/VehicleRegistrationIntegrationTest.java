/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */

/**
 *
 * @author 86139
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
            creds.setUsername("root");
            creds.setPassword("1234");

            VehicleBusinessLogic vehicleLogic = new VehicleBusinessLogic(creds);

            // 定义要注册的新车辆数据
            String type = "Diesel Bus";
            String number = "B999";
            String fuelType = "Diesel";
            double consumptionRate = 0.55;
            int maxPassengers = 45;
            String route = "Route Z";

            // 执行注册
            vehicleLogic.addVehicle(type, number, fuelType, consumptionRate, maxPassengers, route);

            // 从数据库中获取所有车辆，确认新车是否存在
            List<Vehicle> vehicles = vehicleLogic.getAllVehicles();
            boolean exists = vehicles.stream().anyMatch(v -> v.getNumber().equals("B999") && v.getRoute().equals("Route Z"));

            assertTrue(exists, "Newly registered vehicle should exist in database");

        } catch (SQLException e) {
            fail("SQLException during registration: " + e.getMessage());
        }
    }
}

