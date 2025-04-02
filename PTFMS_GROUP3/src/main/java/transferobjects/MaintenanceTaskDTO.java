package transferobjects;

/**
 * DTO for transferring maintenance task data between layers.
 * @author Hongchen Guo
 */
public class MaintenanceTaskDTO {
    private int taskId;
    private int componentId;
    private String vehicleId;
    private String description;
    private String scheduledDate;
    private String status;
    private Integer technicianId;

    /**
     * Gets the task ID.
     * @return the task ID
     */
    public int getTaskId() {
        return taskId;
    }

    /**
     * Sets the task ID.
     * @param taskId the task ID
     */
    public void setTaskId(int taskId) {
        this.taskId = taskId;
    }

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
     * Gets the description.
     * @return the description
     */
    public String getDescription() {
        return description;
    }

    /**
     * Sets the description.
     * @param description the description
     */
    public void setDescription(String description) {
        this.description = description;
    }

    /**
     * Gets the scheduled date.
     * @return the scheduled date
     */
    public String getScheduledDate() {
        return scheduledDate;
    }

    /**
     * Sets the scheduled date.
     * @param scheduledDate the scheduled date
     */
    public void setScheduledDate(String scheduledDate) {
        this.scheduledDate = scheduledDate;
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

    /**
     * Gets the technician ID.
     * @return the technician ID
     */
    public Integer getTechnicianId() {
        return technicianId;
    }

    /**
     * Sets the technician ID.
     * @param technicianId the technician ID
     */
    public void setTechnicianId(Integer technicianId) {
        this.technicianId = technicianId;
    }
}