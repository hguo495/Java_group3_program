package transferobjects;

/**
 * DTO for transferring energy usage data between layers.
 * @author Hongchen Guo
 * Modified by: Wenjuan Qi
 */
public class EnergyUsageDTO {
    private int usageId;
    private String vehicleId;
    private String fuelEnergyType;
    private double amountUsed;
    private double distanceTraveled;
    private String timestamp;

    /**
     * Gets the usage ID.
     * @return the usage ID
     */
    public int getUsageId() {
        return usageId;
    }

    /**
     * Sets the usage ID.
     * @param usageId the usage ID
     */
    public void setUsageId(int usageId) {
        this.usageId = usageId;
    }

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
     * Gets the fuel/energy type.
     * @return the fuel/energy type
     */
    public String getFuelEnergyType() {
        return fuelEnergyType;
    }

    /**
     * Sets the fuel/energy type.
     * @param fuelEnergyType the fuel/energy type
     */
    public void setFuelEnergyType(String fuelEnergyType) {
        this.fuelEnergyType = fuelEnergyType;
    }

    /**
     * Gets the amount used.
     * @return the amount used
     */
    public double getAmountUsed() {
        return amountUsed;
    }

    /**
     * Sets the amount used.
     * @param amountUsed the amount used
     */
    public void setAmountUsed(double amountUsed) {
        this.amountUsed = amountUsed;
    }

    /**
     * Gets the distance traveled.
     * @return the distance traveled
     */
    public double getDistanceTraveled() {
        return distanceTraveled;
    }

    /**
     * Sets the distance traveled.
     * @param distanceTraveled the distance traveled
     */
    public void setDistanceTraveled(double distanceTraveled) {
        this.distanceTraveled = distanceTraveled;
    }

    /**
     * Gets the timestamp.
     * @return the timestamp
     */
    public String getTimestamp() {
        return timestamp;
    }

    /**
     * Sets the timestamp.
     * @param timestamp the timestamp
     */
    public void setTimestamp(String timestamp) {
        this.timestamp = timestamp;
    }
}