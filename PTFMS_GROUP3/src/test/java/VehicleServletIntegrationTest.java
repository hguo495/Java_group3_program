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

import static org.junit.jupiter.api.Assertions.*;

import java.sql.SQLException;
import java.util.List;
import businesslayer.VehicleBuilder;
public class VehicleServletIntegrationTest {

    @Test
    public void testVehicleRegistrationFlow() {
        try {
            // 准备测试数据
            CredentialsDTO creds = new CredentialsDTO();
            creds.setUsername("root");
            creds.setPassword("1234");

            VehicleBusinessLogic vehicleLogic = new VehicleBusinessLogic(creds);
            
            vehicleLogic.deleteVehicle("B999");
            VehicleBuilder builder = new VehicleBuilder()
                .setVehicleId("B999")
                .setType("Diesel Bus")
                .setNumber("B999")
                .setFuelType("Diesel")
                .setConsumptionRate(0.5)
                .setMaxPassengers(50)
                .setRoute("Route X")
                .setStatus("Active");
            String testNumber = "B999";
            String testId = "TEST_BUS_999";

            // 确保数据库中没有重复
            try {
                vehicleLogic.deleteVehicle(testId);
            } catch (Exception ignore) {}

            // 注册新车辆
            vehicleLogic.addVehicle("Diesel Bus", testNumber, "Diesel", 0.5, 60, "Test Route");

            // 获取所有车辆
            List<Vehicle> vehicles =vehicleLogic.getAllVehicles();

            // 查找测试车辆
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

