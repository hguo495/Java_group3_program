/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */

/**
 *
 * @author 86139
 */
import businesslayer.DieselStrategy;
import businesslayer.EnergyStrategy;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class DieselStrategyTest {

    @Test
    public void testIsEfficient_WithHighEfficiency_ReturnsTrue() {
        EnergyStrategy strategy = new DieselStrategy();
        double amountUsed = 10.0;
        double distanceTraveled = 60.0;

        boolean result = strategy.isEfficient(amountUsed, distanceTraveled);

        assertTrue(result, "DieselStrategy should return true for 6.0 mpg");
    }

    @Test
    public void testIsEfficient_WithLowEfficiency_ReturnsFalse() {
        EnergyStrategy strategy = new DieselStrategy();
        double amountUsed = 15.0;
        double distanceTraveled = 60.0;

        boolean result = strategy.isEfficient(amountUsed, distanceTraveled);

        assertFalse(result, "DieselStrategy should return false for 4.0 mpg");
    }
}

