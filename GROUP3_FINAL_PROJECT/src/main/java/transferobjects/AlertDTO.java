package transferobjects;

/**
 * DTO for transferring alert data between layers.
 * @author Hong Chen
 * Modified by: Wenjuan Qi
 */
public class AlertDTO {
    private int alertId;
    private String type;
    private String vehicleId;
    private String message;
    private String timestamp;
    private String status;

    /**
     * Gets the alert ID.
     * @return the alert ID
     */
    public int getAlertId() {
        return alertId;
    }

    /**
     * Sets the alert ID.
     * @param alertId the alert ID
     */
    public void setAlertId(int alertId) {
        this.alertId = alertId;
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
     * Gets the message.
     * @return the message
     */
    public String getMessage() {
        return message;
    }

    /**
     * Sets the message.
     * @param message the message
     */
    public void setMessage(String message) {
        this.message = message;
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