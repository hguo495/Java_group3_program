package dataaccesslayer;

import entity.StationEvent;
import transferobjects.CredentialsDTO;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**
 * DAO implementation for station event operations.
 * @author Jinze Li
 */
public class StationEventDAOImpl implements StationEventDAO {
    private final Connection connection;

    /**
     * Constructor that initializes the database connection.
     * @param creds the credentials to connect to the database
     * @throws SQLException if a database error occurs
     */
    public StationEventDAOImpl(CredentialsDTO creds) throws SQLException {
        DataSource dataSource = DataSource.getInstance(creds);
        this.connection = dataSource.getConnection();
    }

    @Override
    public List<StationEvent> getAllStationEvents() throws SQLException {
        List<StationEvent> events = new ArrayList<>();
        String query = "SELECT * FROM station_events";
        try (PreparedStatement stmt = connection.prepareStatement(query);
             ResultSet rs = stmt.executeQuery()) {
            while (rs.next()) {
                StationEvent event = new StationEvent();
                event.setEventId(rs.getInt("event_id"));
                event.setVehicleId(rs.getString("vehicle_id"));
                event.setStationId(rs.getString("station_id"));
                event.setEventType(rs.getString("event_type"));
                event.setTimestamp(rs.getString("timestamp"));
                events.add(event);
            }
        }
        return events;
    }

    @Override
    public List<StationEvent> getStationEventsByVehicle(String vehicleId) throws SQLException {
        List<StationEvent> events = new ArrayList<>();
        String query = "SELECT * FROM station_events WHERE vehicle_id = ? ORDER BY timestamp";
        try (PreparedStatement stmt = connection.prepareStatement(query)) {
            stmt.setString(1, vehicleId);
            try (ResultSet rs = stmt.executeQuery()) {
                while (rs.next()) {
                    StationEvent event = new StationEvent();
                    event.setEventId(rs.getInt("event_id"));
                    event.setVehicleId(rs.getString("vehicle_id"));
                    event.setStationId(rs.getString("station_id"));
                    event.setEventType(rs.getString("event_type"));
                    event.setTimestamp(rs.getString("timestamp"));
                    events.add(event);
                }
            }
        }
        return events;
    }

    @Override
    public List<StationEvent> getStationEventsByStation(String stationId) throws SQLException {
        List<StationEvent> events = new ArrayList<>();
        String query = "SELECT * FROM station_events WHERE station_id = ? ORDER BY timestamp";
        try (PreparedStatement stmt = connection.prepareStatement(query)) {
            stmt.setString(1, stationId);
            try (ResultSet rs = stmt.executeQuery()) {
                while (rs.next()) {
                    StationEvent event = new StationEvent();
                    event.setEventId(rs.getInt("event_id"));
                    event.setVehicleId(rs.getString("vehicle_id"));
                    event.setStationId(rs.getString("station_id"));
                    event.setEventType(rs.getString("event_type"));
                    event.setTimestamp(rs.getString("timestamp"));
                    events.add(event);
                }
            }
        }
        return events;
    }

    @Override
    public void addStationEvent(StationEvent event) throws SQLException {
        String query = "INSERT INTO station_events (vehicle_id, station_id, event_type, timestamp) VALUES (?, ?, ?, ?)";
        try (PreparedStatement stmt = connection.prepareStatement(query)) {
            stmt.setString(1, event.getVehicleId());
            stmt.setString(2, event.getStationId());
            stmt.setString(3, event.getEventType());
            stmt.setString(4, event.getTimestamp());
            stmt.executeUpdate();
        }
    }
}