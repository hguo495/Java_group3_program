package dataaccesslayer;

import entity.GPSTracking;
import transferobjects.CredentialsDTO;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**
 * DAO implementation for GPS tracking operations.
 * @author Jinze Li
 */
public class GPSTrackingDAOImpl implements GPSTrackingDAO {
    private final Connection connection;

    /**
     * Constructor that initializes the database connection.
     * @param creds the credentials to connect to the database
     * @throws SQLException if a database error occurs
     */
    public GPSTrackingDAOImpl(CredentialsDTO creds) throws SQLException {
        DataSource dataSource = DataSource.getInstance(creds);
        this.connection = dataSource.getConnection();
    }

    @Override
    public List<GPSTracking> getAllGPSTracking() throws SQLException {
        List<GPSTracking> gpsList = new ArrayList<>();
        String query = "SELECT * FROM gps_tracking";
        try (PreparedStatement stmt = connection.prepareStatement(query);
             ResultSet rs = stmt.executeQuery()) {
            while (rs.next()) {
                GPSTracking gps = new GPSTracking();
                gps.setTrackingId(rs.getInt("tracking_id"));
                gps.setVehicleId(rs.getString("vehicle_id"));
                gps.setLatitude(rs.getDouble("latitude"));
                gps.setLongitude(rs.getDouble("longitude"));
                gps.setTimestamp(rs.getString("timestamp"));
                gps.setStationId(rs.getString("station_id"));
                gpsList.add(gps);
            }
        }
        return gpsList;
    }
    @Override
public List<GPSTracking> getRecentTrackingByVehicle(String vehicleId, int limit) throws SQLException {
    List<GPSTracking> gpsList = new ArrayList<>();
    String query = "SELECT * FROM gps_tracking WHERE vehicle_id = ? ORDER BY timestamp DESC LIMIT ?";
    try (PreparedStatement stmt = connection.prepareStatement(query)) {
        stmt.setString(1, vehicleId);
        stmt.setInt(2, limit);
        try (ResultSet rs = stmt.executeQuery()) {
            while (rs.next()) {
                GPSTracking gps = new GPSTracking();
                gps.setTrackingId(rs.getInt("tracking_id"));
                gps.setVehicleId(rs.getString("vehicle_id"));
                gps.setLatitude(rs.getDouble("latitude"));
                gps.setLongitude(rs.getDouble("longitude"));
                gps.setTimestamp(rs.getString("timestamp"));
                gps.setStationId(rs.getString("station_id"));
                gpsList.add(gps);
            }
        }
    }
    return gpsList;
}

    @Override
    public void addGPSTracking(GPSTracking gps) throws SQLException {
        String query = "INSERT INTO gps_tracking (vehicle_id, latitude, longitude, timestamp, station_id) VALUES (?, ?, ?, ?, ?)";
        try (PreparedStatement stmt = connection.prepareStatement(query)) {
            stmt.setString(1, gps.getVehicleId());
            stmt.setDouble(2, gps.getLatitude());
            stmt.setDouble(3, gps.getLongitude());
            stmt.setString(4, gps.getTimestamp());
            stmt.setString(5, gps.getStationId());
            stmt.executeUpdate();
        }
    }
}