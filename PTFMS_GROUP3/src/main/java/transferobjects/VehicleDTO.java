package transferobjects;

/**
 * DTO for transferring vehicle data between layers.
 * @author Hongchen Guo
 */
public class VehicleDTO {
    private String vehicleId;
    private String type;
    private String number;
    private String fuelType;
    private double consumptionRate;
    private int maxPassengers;
    private String route;
    private String status;

    /**
     * Gets the vehicle ID.
     * @return the vehicle ID
     */
    public String getVehicleId() {
        return vehicleId;
    }

    /**
     * Sets the vehicle ID.
     * @param vehicleId the vehicle ID
     */
    public void setVehicleId(String vehicleId) {
        this.vehicleId = vehicleId;
    }

    /**
     * Gets the type.
     * @return the type
     */
    public String getType() {
        return type;
    }

    /**
     * Sets the type.
     * @param type the type
     */
    public void setType(String type) {
        this.type = type;
    }

    /**
     * Gets the number.
     * @return the number
     */
    public String getNumber() {
        return number;
    }

    /**
     * Sets the number.
     * @param number the number
     */
    public void setNumber(String number) {
        this.number = number;
    }

    /**
     * Gets the fuel type.
     * @return the fuel type
     */
    public String getFuelType() {
        return fuelType;
    }

    /**
     * Sets the fuel type.
     * @param fuelType the fuel type
     */
    public void setFuelType(String fuelType) {
        this.fuelType = fuelType;
    }

    /**
     * Gets the consumption rate.
     * @return the consumption rate
     */
    public double getConsumptionRate() {
        return consumptionRate;
    }

    /**
     * Sets the consumption rate.
     * @param consumptionRate the consumption rate
     */
    public void setConsumptionRate(double consumptionRate) {
        this.consumptionRate = consumptionRate;
    }

    /**
     * Gets the maximum passengers.
     * @return the maximum passengers
     */
    public int getMaxPassengers() {
        return maxPassengers;
    }

    /**
     * Sets the maximum passengers.
     * @param maxPassengers the maximum passengers
     */
    public void setMaxPassengers(int maxPassengers) {
        this.maxPassengers = maxPassengers;
    }

    /**
     * Gets the route.
     * @return the route
     */
    public String getRoute() {
        return route;
    }

    /**
     * Sets the route.
     * @param route the route
     */
    public void setRoute(String route) {
        this.route = route;
    }

    /**
     * Gets the status.
     * @return the status
     */
    public String getStatus() {
        return status;
    }

    /**
     * Sets the status.
     * @param status the status
     */
    public void setStatus(String status) {
        this.status = status;
    }
}