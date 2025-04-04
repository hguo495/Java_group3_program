/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */

/**
 *
 * @author 86139
 */
import businesslayer.VehicleBuilder;
import entity.Vehicle;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class VehicleBuilderTest {

    @Test
    public void testBuildDieselBus() {
        // Arrange
        VehicleBuilder builder = new VehicleBuilder();

        // Act
        Vehicle vehicle = builder.setVehicleId("BUS123")
                                  .setType("Diesel Bus")
                                  .setNumber("B123")
                                  .setFuelType("Diesel")
                                  .setConsumptionRate(0.5)
                                  .setMaxPassengers(50)
                                  .setRoute("Route A")
                                  .setStatus("Active")
                                  .build();

        // Assert
        assertNotNull(vehicle);
        assertEquals("BUS123", vehicle.getVehicleId());
        assertEquals("Diesel Bus", vehicle.getType());
        assertEquals("B123", vehicle.getNumber());
        assertEquals("Diesel", vehicle.getFuelType());
        assertEquals(0.5, vehicle.getConsumptionRate(), 0.01);
        assertEquals(50, vehicle.getMaxPassengers());
        assertEquals("Route A", vehicle.getRoute());
        assertEquals("Active", vehicle.getStatus());
    }
}

