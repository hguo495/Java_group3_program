/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */

/**
 *
 * @author 86139
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
            creds.setUsername("root");
            creds.setPassword("1234");

            ComponentBusinessLogic componentLogic = new ComponentBusinessLogic(creds);
            AlertBusinessLogic alertLogic = new AlertBusinessLogic(creds);

            // 创建超出寿命限制的组件
            Component component = new Component();
            component.setVehicleId("BUS001");
            component.setType("Axle Bearing");
            component.setHoursUsed(11000);  // 超出 10000
            component.setWearPercentage(85); // 超出 80

            componentLogic.addComponent(component);

            // 验证是否触发了 Maintenance 警报
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

