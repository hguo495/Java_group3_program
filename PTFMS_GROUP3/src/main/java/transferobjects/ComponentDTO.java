package transferobjects;

/**
 * DTO for transferring component data between layers.
 * @author Hongchen Guo
 */
public class ComponentDTO {
    private int componentId;
    private String vehicleId;
    private String type;
    private double hoursUsed;
    private double wearPercentage;
    private String diagnosticStatus;

    /**
     * Gets the component ID.
     * @return the component ID
     */
    public int getComponentId() {
        return componentId;
    }

    /**
     * Sets the component ID.
     * @param componentId the component ID
     */
    public void setComponentId(int componentId) {
        this.componentId = componentId;
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
     * Gets the hours used.
     * @return the hours used
     */
    public double getHoursUsed() {
        return hoursUsed;
    }

    /**
     * Sets the hours used.
     * @param hoursUsed the hours used
     */
    public void setHoursUsed(double hoursUsed) {
        this.hoursUsed = hoursUsed;
    }

    /**
     * Gets the wear percentage.
     * @return the wear percentage
     */
    public double getWearPercentage() {
        return wearPercentage;
    }

    /**
     * Sets the wear percentage.
     * @param wearPercentage the wear percentage
     */
    public void setWearPercentage(double wearPercentage) {
        this.wearPercentage = wearPercentage;
    }

    /**
     * Gets the diagnostic status.
     * @return the diagnostic status
     */
    public String getDiagnosticStatus() {
        return diagnosticStatus;
    }

    /**
     * Sets the diagnostic status.
     * @param diagnosticStatus the diagnostic status
     */
    public void setDiagnosticStatus(String diagnosticStatus) {
        this.diagnosticStatus = diagnosticStatus;
    }
}