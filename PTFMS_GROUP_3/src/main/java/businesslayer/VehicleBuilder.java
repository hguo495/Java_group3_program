package businesslayer;

import entity.Vehicle;

/**
 * Builder class for constructing Vehicle objects.
 * Implements Builder pattern.
 * @author Hongchen Guo
 */
public class VehicleBuilder {
    private Vehicle vehicle;

    /**
     * Constructor that initializes a new Vehicle.
     */
    public VehicleBuilder() {
        this.vehicle = new Vehicle();
    }

    /**
     * Sets the vehicle ID.
     * @param vehicleId the vehicle ID
     * @return this builder
     */
    public VehicleBuilder setVehicleId(String vehicleId) {
        vehicle.setVehicleId(vehicleId);
        return this;
    }

    /**
     * Sets the type.
     * @param type the type
     * @return this builder
     */
    public VehicleBuilder setType(String type) {
        vehicle.setType(type);
        return this;
    }

    /**
     * Sets the number.
     * @param number the number
     * @return this builder
     */
    public VehicleBuilder setNumber(String number) {
        vehicle.setNumber(number);
        return this;
    }

    /**
     * Sets the fuel type.
     * @param fuelType the fuel type
     * @return this builder
     */
    public VehicleBuilder setFuelType(String fuelType) {
        vehicle.setFuelType(fuelType);
        return this;
    }

    /**
     * Sets the consumption rate.
     * @param consumptionRate the consumption rate
     * @return this builder
     */
    public VehicleBuilder setConsumptionRate(double consumptionRate) {
        vehicle.setConsumptionRate(consumptionRate);
        return this;
    }

    /**
     * Sets the maximum passengers.
     * @param maxPassengers the maximum passengers
     * @return this builder
     */
    public VehicleBuilder setMaxPassengers(int maxPassengers) {
        vehicle.setMaxPassengers(maxPassengers);
        return this;
    }

    /**
     * Sets the route.
     * @param route the route
     * @return this builder
     */
    public VehicleBuilder setRoute(String route) {
        vehicle.setRoute(route);
        return this;
    }

    /**
     * Sets the status.
     * @param status the status
     * @return this builder
     */
    public VehicleBuilder setStatus(String status) {
        vehicle.setStatus(status);
        return this;
    }

    /**
     * Builds the Vehicle object.
     * @return the constructed Vehicle
     */
    public Vehicle build() {
        return vehicle;
    }
}