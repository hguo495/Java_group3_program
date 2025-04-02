package transferobjects;

/**
 * DTO for transferring operator log data between layers.
 * @author Hongchen Guo
 */
public class OperatorLogDTO {
    private int logId;
    private String vehicleId;
    private String startTime;
    private String endTime;
    private String eventType;
    private int operatorId;

    /**
     * Gets the log ID.
     * @return the log ID
     */
    public int getLogId() {
        return logId;
    }

    /**
     * Sets the log ID.
     * @param logId the log ID
     */
    public void setLogId(int logId) {
        this.logId = logId;
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
     * Gets the start time.
     * @return the start time
     */
    public String getStartTime() {
        return startTime;
    }

    /**
     * Sets the start time.
     * @param startTime the start time
     */
    public void setStartTime(String startTime) {
        this.startTime = startTime;
    }

    /**
     * Gets the end time.
     * @return the end time
     */
    public String getEndTime() {
        return endTime;
    }

    /**
     * Sets the end time.
     * @param endTime the end time
     */
    public void setEndTime(String endTime) {
        this.endTime = endTime;
    }

    /**
     * Gets the event type.
     * @return the event type
     */
    public String getEventType() {
        return eventType;
    }

    /**
     * Sets the event type.
     * @param eventType the event type
     */
    public void setEventType(String eventType) {
        this.eventType = eventType;
    }

    /**
     * Gets the operator ID.
     * @return the operator ID
     */
    public int getOperatorId() {
        return operatorId;
    }

    /**
     * Sets the operator ID.
     * @param operatorId the operator ID
     */
    public void setOperatorId(int operatorId) {
        this.operatorId = operatorId;
    }
}