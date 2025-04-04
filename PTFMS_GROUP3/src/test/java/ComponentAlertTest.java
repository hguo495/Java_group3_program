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
            creds.setUsername("root");
            creds.setPassword("1234");

            ComponentBusinessLogic componentLogic = new ComponentBusinessLogic(creds);
            AlertBusinessLogic alertLogic = new AlertBusinessLogic(creds);

            // 创建一个超过阈值的组件（如使用了 90000 小时）
            Component component = new Component();
            component.setVehicleId("BUS001");
            component.setType("Brakes");
            component.setHoursUsed(90000); // 超过 10 年
            component.setWearPercentage(85); // 高磨损
            component.setDiagnosticStatus("Warning");

            componentLogic.addComponent(component);

            // 获取所有警报并验证是否包含刚插入的那条车辆的维护类型警报
            List<Alert> alerts = alertLogic.getFilteredAlerts("Maintenance", "Pending", "BUS001");

            boolean found = alerts.stream().anyMatch(a -> a.getMessage().toLowerCase().contains("maintenance required"));
            assertTrue(found, "Alert should be triggered for component exceeding wear/usage threshold");

        } catch (SQLException e) {
            fail("SQLException: " + e.getMessage());
        }
    }
}

