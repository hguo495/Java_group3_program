package businesslayer;

import dataaccesslayer.StationDAO;
import dataaccesslayer.StationDAOImpl;
import dataaccesslayer.StationEventDAO;
import dataaccesslayer.StationEventDAOImpl;
import entity.Station;
import entity.StationEvent;
import transferobjects.CredentialsDTO;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * Business logic for station tracking and reporting.
 * @author Hongchen Guo
 */
public class StationTrackingBusinessLogic {
    public StationDAO stationDAO;
    private StationEventDAO stationEventDAO;

    /**
     * Constructor that initializes the DAOs.
     * @param creds the credentials to connect to the database
     * @throws SQLException if a database error occurs
     */
    public StationTrackingBusinessLogic(CredentialsDTO creds) throws SQLException {
        this.stationDAO = new StationDAOImpl(creds);
        this.stationEventDAO = new StationEventDAOImpl(creds);
    }

    /**
     * Records a vehicle's arrival at a station.
     * @param vehicleId the vehicle ID
     * @param stationId the station ID
     * @throws SQLException if a database error occurs
     */
    public void recordArrival(String vehicleId, String stationId) throws SQLException {
        StationEvent event = new StationEvent();
        event.setVehicleId(vehicleId);
        event.setStationId(stationId);
        event.setEventType("ARRIVAL");
        event.setTimestamp(String.valueOf(System.currentTimeMillis()));
        stationEventDAO.addStationEvent(event);
    }

    /**
     * Records a vehicle's departure from a station.
     * @param vehicleId the vehicle ID
     * @param stationId the station ID
     * @throws SQLException if a database error occurs
     */
    public void recordDeparture(String vehicleId, String stationId) throws SQLException {
        StationEvent event = new StationEvent();
        event.setVehicleId(vehicleId);
        event.setStationId(stationId);
        event.setEventType("DEPARTURE");
        event.setTimestamp(String.valueOf(System.currentTimeMillis()));
        stationEventDAO.addStationEvent(event);
    }

    /**
     * Gets all station events for a vehicle.
     * @param vehicleId the vehicle ID
     * @return a list of all station events for the vehicle
     * @throws SQLException if a database error occurs
     */
    public List<StationEvent> getVehicleEvents(String vehicleId) throws SQLException {
        return stationEventDAO.getStationEventsByVehicle(vehicleId);
    }

    /**
     * Gets all station events for a station.
     * @param stationId the station ID
     * @return a list of all station events for the station
     * @throws SQLException if a database error occurs
     */
    public List<StationEvent> getStationEvents(String stationId) throws SQLException {
        return stationEventDAO.getStationEventsByStation(stationId);
    }

    /**
     * Generates a station arrival/departure report for a vehicle.
     * This pairs arrival and departure events to create a complete journey record.
     * @param vehicleId the vehicle ID
     * @return a list of arrival/departure pairs with station information
     * @throws SQLException if a database error occurs
     */
    public List<Map<String, Object>> getVehicleStationReport(String vehicleId) throws SQLException {
        // Get all station events for this vehicle
        List<StationEvent> events = stationEventDAO.getStationEventsByVehicle(vehicleId);
        Map<String, String> arrivalTimes = new HashMap<>();
        List<Map<String, Object>> report = new ArrayList<>();

        // Process events and pair arrivals with departures
        for (StationEvent event : events) {
            String stationId = event.getStationId();
            
            if ("ARRIVAL".equals(event.getEventType())) {
                // Store arrival time for this station
                arrivalTimes.put(stationId, event.getTimestamp());
            } else if ("DEPARTURE".equals(event.getEventType())) {
                // Find corresponding arrival time
                String arrivalTime = arrivalTimes.get(stationId);
                if (arrivalTime != null) {
                    // Get station details
                    Station station = stationDAO.getStationById(stationId);
                    
                    // Create a report entry with station details and timing
                    Map<String, Object> entry = new HashMap<>();
                    entry.put("stationId", stationId);
                    entry.put("stationName", station.getName());
                    entry.put("arrivalTime", arrivalTime);
                    entry.put("departureTime", event.getTimestamp());
                    
                    // Calculate dwell time (time spent at the station)
                    long dwellTimeMs = Long.parseLong(event.getTimestamp()) - Long.parseLong(arrivalTime);
                    entry.put("dwellTimeMinutes", dwellTimeMs / 60000); // Convert ms to minutes
                    
                    report.add(entry);
                    arrivalTimes.remove(stationId); // Clean up processed entry
                }
            }
        }
        
        return report;
    }

    /**
     * Generates a comprehensive report of all vehicles and their station visits.
     * @return a map of vehicle IDs to their station reports
     * @throws SQLException if a database error occurs
     */
    public Map<String, List<Map<String, Object>>> getAllVehicleStationReports() throws SQLException {
        // Get all station events
        List<StationEvent> allEvents = stationEventDAO.getAllStationEvents();
        Map<String, List<Map<String, Object>>> allReports = new HashMap<>();
        
        // Extract unique vehicle IDs
        Set<String> vehicleIds = new HashSet<>();
        for (StationEvent event : allEvents) {
            vehicleIds.add(event.getVehicleId());
        }
        
        // Generate report for each vehicle
        for (String vehicleId : vehicleIds) {
            allReports.put(vehicleId, getVehicleStationReport(vehicleId));
        }
        
        return allReports;
    }
    
    /**
     * Gets a list of all stations.
     * @return a list of all stations
     * @throws SQLException if a database error occurs
     */
    public List<Station> getAllStations() throws SQLException {
        return stationDAO.getAllStations();
    }
    
    /**
     * Gets a list of stations on a specific route.
     * @param routeId the route ID
     * @return a list of stations on the route
     * @throws SQLException if a database error occurs
     */
    public List<Station> getStationsByRoute(String routeId) throws SQLException {
        return stationDAO.getStationsByRoute(routeId);
    }
    
    /**
     * Adds a new station to the system.
     * @param station the station to add
     * @throws SQLException if a database error occurs
     */
    public void addStation(Station station) throws SQLException {
        stationDAO.addStation(station);
    }
}