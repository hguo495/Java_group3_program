package businesslayer;

import businesslayer.VehicleBuilder;

/**
 * Factory class to create VehicleBuilder instances based on type.
 * Implements Simple Factory pattern.
 * @author Hongchen Guo
 */
public class VehicleFactory {
    /**
     * Creates a VehicleBuilder based on vehicle type.
     * @param type the type of vehicle
     * @return a configured VehicleBuilder
     */
    public VehicleBuilder createVehicle(String type) {
        switch (type) {
            case "Diesel Bus":
                return new VehicleBuilder().setType("Diesel Bus").setFuelType("Diesel");
            case "Electric Light Rail":
                return new VehicleBuilder().setType("Electric Light Rail").setFuelType("Electric");
            case "Diesel-Electric Train":
                return new VehicleBuilder().setType("Diesel-Electric Train").setFuelType("Diesel");
            default:
                throw new IllegalArgumentException("Unknown vehicle type: " + type);
        }
    }
}