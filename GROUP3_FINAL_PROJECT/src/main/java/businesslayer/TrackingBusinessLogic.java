package businesslayer;

import dataaccesslayer.GPSTrackingDAO;
import dataaccesslayer.GPSTrackingDAOImpl;
import dataaccesslayer.OperatorLogDAO;
import dataaccesslayer.OperatorLogDAOImpl;
import entity.GPSTracking;
import entity.OperatorLog;
import transferobjects.CredentialsDTO;
import java.sql.SQLException;
import java.util.List;
import java.util.Map;

/**
 * Business logic for tracking operations.
 * @author Hongchen Guo
 */
public class TrackingBusinessLogic {
    private GPSTrackingDAO gpsTrackingDAO;
    private OperatorLogDAO operatorLogDAO;
    private StationTrackingBusinessLogic stationTrackingLogic;

    /**
     * Constructor that initializes the DAOs.
     * @param creds the credentials to connect to the database
     * @throws SQLException if a database error occurs
     */
    public TrackingBusinessLogic(CredentialsDTO creds) throws SQLException {
        this.gpsTrackingDAO = new GPSTrackingDAOImpl(creds);
        this.operatorLogDAO = new OperatorLogDAOImpl(creds);
        this.stationTrackingLogic = new StationTrackingBusinessLogic(creds);
    }

    /**
     * Gets all GPS tracking records.
     * @return a list of all GPS tracking records
     * @throws SQLException if a database error occurs
     */
    public List<GPSTracking> getAllGPSTracking() throws SQLException {
        return gpsTrackingDAO.getAllGPSTracking();
    }

    /**
     * Adds a new GPS tracking record and detects station arrivals/departures.
     * @param gps the GPS tracking record to add
     * @throws SQLException if a database error occurs
     */
    public void addGPSTracking(GPSTracking gps) throws SQLException {
        gpsTrackingDAO.addGPSTracking(gps);
        detectStationArrivalDeparture(gps);
    }

    /**
     * Gets all operator logs.
     * @return a list of all operator logs
     * @throws SQLException if a database error occurs
     */
    public List<OperatorLog> getAllOperatorLogs() throws SQLException {
        return operatorLogDAO.getAllOperatorLogs();
    }

    /**
     * Adds a new operator log.
     * @param log the operator log to add
     * @throws SQLException if a database error occurs
     */
    public void addOperatorLog(OperatorLog log) throws SQLException {
        operatorLogDAO.addOperatorLog(log);
    }
    
    /**
     * Detects if a vehicle has arrived at or departed from a station based on GPS data.
     * @param gps the GPS tracking record to analyze
     * @throws SQLException if a database error occurs
     */
    private void detectStationArrivalDeparture(GPSTracking gps) throws SQLException {
        // Check if the GPS record has station information
        if (gps.getStationId() != null && !gps.getStationId().isEmpty()) {
            // Get the most recent previous tracking record for this vehicle
            List<GPSTracking> previousTracking = gpsTrackingDAO.getRecentTrackingByVehicle(gps.getVehicleId(), 1);
            
            // If no previous record or previous record was at a different station,
            // this means the vehicle has arrived at a new station
            if (previousTracking.isEmpty() || 
                !gps.getStationId().equals(previousTracking.get(0).getStationId())) {
                // Record the arrival event
                stationTrackingLogic.recordArrival(gps.getVehicleId(), gps.getStationId());
            }
        } else {
            // If the current record has no station ID, check if the vehicle just left a station
            List<GPSTracking> previousTracking = gpsTrackingDAO.getRecentTrackingByVehicle(gps.getVehicleId(), 1);
            
            // If previous record exists and had a station ID, the vehicle has departed from that station
            if (!previousTracking.isEmpty() && 
                previousTracking.get(0).getStationId() != null && 
                !previousTracking.get(0).getStationId().isEmpty()) {
                // Record the departure event
                stationTrackingLogic.recordDeparture(gps.getVehicleId(), previousTracking.get(0).getStationId());
            }
        }
    }
    
    /**
     * Generates a station arrival/departure report for a specific vehicle.
     * @param vehicleId the ID of the vehicle
     * @return a list of arrival/departure events with station information
     * @throws SQLException if a database error occurs
     */
    public List<Map<String, Object>> getVehicleStationReport(String vehicleId) throws SQLException {
        return stationTrackingLogic.getVehicleStationReport(vehicleId);
    }
    
    /**
     * Generates station arrival/departure reports for all vehicles.
     * @return a map of vehicle IDs to their arrival/departure reports
     * @throws SQLException if a database error occurs
     */
    public Map<String, List<Map<String, Object>>> getAllVehicleStationReports() throws SQLException {
        return stationTrackingLogic.getAllVehicleStationReports();
    }
}