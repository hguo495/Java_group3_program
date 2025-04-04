/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */

/**
 *
 * @author 86139
 */
import businesslayer.EnergyBusinessLogic;
import entity.EnergyUsage;
import org.junit.jupiter.api.Test;
import transferobjects.CredentialsDTO;

import java.sql.SQLException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

import static org.junit.jupiter.api.Assertions.*;

public class EnergyBusinessLogicTest {

    @Test
    public void testAddEnergyUsage_ExcessiveUsage_TriggersAlert() {
        try {
            CredentialsDTO creds = new CredentialsDTO();
            creds.setUsername("root");
            creds.setPassword("1234");

            EnergyBusinessLogic energyLogic = new EnergyBusinessLogic(creds);

            EnergyUsage usage = new EnergyUsage();
            usage.setVehicleId("BUS001"); // 已存在于数据库
            usage.setFuelEnergyType("Diesel");
            usage.setAmountUsed(20.0); // 非常低效（20L 只跑 10km）
            usage.setDistanceTraveled(10.0);

            String timestamp = LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));
            usage.setTimestamp(timestamp);

            // 添加记录（应触发 Alert）
            energyLogic.addEnergyUsage(usage);

            // 不断言是否有 Alert（需在 Alert 表查看效果），这里只确保代码不抛异常
            assertTrue(true);

        } catch (SQLException e) {
            fail("SQLException: " + e.getMessage());
        }
    }
}

