package transferobjects;

/**
 * DTO for transferring GPS tracking data between layers.
 * @author Hongchen Guo
 * Modified by: Wenjuan Qi
 */
public class GPSTrackingDTO {
    private int trackingId;
    private String vehicleId;
    private double latitude;
    private double longitude;
    private String timestamp;
    private String stationId;

    /**
     * Gets the tracking ID.
     * @return the tracking ID
     */
    public int getTrackingId() {
        return trackingId;
    }

    /**
     * Sets the tracking ID.
     * @param trackingId the tracking ID
     */
    public void setTrackingId(int trackingId) {
        this.trackingId = trackingId;
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
     * Gets the latitude.
     * @return the latitude
     */
    public double getLatitude() {
        return latitude;
    }

    /**
     * Sets the latitude.
     * @param latitude the latitude
     */
    public void setLatitude(double latitude) {
        this.latitude = latitude;
    }

    /**
     * Gets the longitude.
     * @return the longitude
     */
    public double getLongitude() {
        return longitude;
    }

    /**
     * Sets the longitude.
     * @param longitude the longitude
     */
    public void setLongitude(double longitude) {
        this.longitude = longitude;
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
     * Gets the station ID.
     * @return the station ID
     */
    public String getStationId() {
        return stationId;
    }

    /**
     * Sets the station ID.
     * @param stationId the station ID
     */
    public void setStationId(String stationId) {
        this.stationId = stationId;
    }
}