package entity;

/**
 * Entity class for the operator_trips table.
 * @author Hongchen Guo
 */
public class OperatorTrip {
    private int tripId;
    private int operatorId;
    private String vehicleId;
    private String route;
    private String startTime;
    private String endTime;
    private String scheduledStartTime;
    private String actualStartTime;
    private boolean isOnTime;
    private double distanceTraveled;
    private int tripDuration;
    private int idleTime;
    private double fuelConsumption;
    private int passengerCount;
    private String tripDate;

    /**
     * Gets the trip ID.
     * @return the trip ID
     */
    public int getTripId() {
        return tripId;
    }

    /**
     * Sets the trip ID.
     * @param tripId the trip ID
     */
    public void setTripId(int tripId) {
        this.tripId = tripId;
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
     * Gets the scheduled start time.
     * @return the scheduled start time
     */
    public String getScheduledStartTime() {
        return scheduledStartTime;
    }

    /**
     * Sets the scheduled start time.
     * @param scheduledStartTime the scheduled start time
     */
    public void setScheduledStartTime(String scheduledStartTime) {
        this.scheduledStartTime = scheduledStartTime;
    }

    /**
     * Gets the actual start time.
     * @return the actual start time
     */
    public String getActualStartTime() {
        return actualStartTime;
    }

    /**
     * Sets the actual start time.
     * @param actualStartTime the actual start time
     */
    public void setActualStartTime(String actualStartTime) {
        this.actualStartTime = actualStartTime;
    }

    /**
     * Checks if the trip was on time.
     * @return true if the trip was on time, false otherwise
     */
    public boolean isOnTime() {
        return isOnTime;
    }

    /**
     * Sets whether the trip was on time.
     * @param onTime true if the trip was on time, false otherwise
     */
    public void setOnTime(boolean onTime) {
        isOnTime = onTime;
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
     * Gets the trip duration.
     * @return the trip duration in minutes
     */
    public int getTripDuration() {
        return tripDuration;
    }

    /**
     * Sets the trip duration.
     * @param tripDuration the trip duration in minutes
     */
    public void setTripDuration(int tripDuration) {
        this.tripDuration = tripDuration;
    }

    /**
     * Gets the idle time.
     * @return the idle time in minutes
     */
    public int getIdleTime() {
        return idleTime;
    }

    /**
     * Sets the idle time.
     * @param idleTime the idle time in minutes
     */
    public void setIdleTime(int idleTime) {
        this.idleTime = idleTime;
    }

    /**
     * Gets the fuel consumption.
     * @return the fuel consumption
     */
    public double getFuelConsumption() {
        return fuelConsumption;
    }

    /**
     * Sets the fuel consumption.
     * @param fuelConsumption the fuel consumption
     */
    public void setFuelConsumption(double fuelConsumption) {
        this.fuelConsumption = fuelConsumption;
    }

    /**
     * Gets the passenger count.
     * @return the passenger count
     */
    public int getPassengerCount() {
        return passengerCount;
    }

    /**
     * Sets the passenger count.
     * @param passengerCount the passenger count
     */
    public void setPassengerCount(int passengerCount) {
        this.passengerCount = passengerCount;
    }

    /**
     * Gets the trip date.
     * @return the trip date
     */
    public String getTripDate() {
        return tripDate;
    }

    /**
     * Sets the trip date.
     * @param tripDate the trip date
     */
    public void setTripDate(String tripDate) {
        this.tripDate = tripDate;
    }
}