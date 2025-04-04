package dataaccesslayer;

import entity.Alert;
import transferobjects.CredentialsDTO;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

/**
 * DAO implementation for alert operations.
 * Ensures prevention of duplicate alerts by checking for existing ones before insertion.
 * 
 * @author Jinze Li
 */
public class AlertDAOImpl implements AlertDAO {
    private final Connection connection;

    /**
     * Initializes the AlertDAOImpl with a database connection.
     * @param creds Credentials for connecting to the database
     * @throws SQLException if connection fails
     */
    public AlertDAOImpl(CredentialsDTO creds) throws SQLException {
        DataSource dataSource = DataSource.getInstance(creds);
        this.connection = dataSource.getConnection();
    }

    @Override
    public List<Alert> getAllAlerts() throws SQLException {
        List<Alert> alerts = new ArrayList<>();
        String query = "SELECT * FROM alerts";
        try (PreparedStatement stmt = connection.prepareStatement(query);
             ResultSet rs = stmt.executeQuery()) {
            while (rs.next()) {
                alerts.add(mapResultSetToAlert(rs));
            }
        }
        return alerts;
    }

    @Override
    public void addAlert(Alert alert) throws SQLException {
        String query = "INSERT INTO alerts (type, vehicle_id, message, timestamp, status) VALUES (?, ?, ?, ?, ?)";
        try (PreparedStatement stmt = connection.prepareStatement(query)) {
            stmt.setString(1, alert.getType());
            stmt.setString(2, alert.getVehicleId());
            stmt.setString(3, alert.getMessage());
            stmt.setString(4, alert.getTimestamp());
            stmt.setString(5, alert.getStatus());
            stmt.executeUpdate();
        }
    }

    @Override
    public Alert getAlertById(int alertId) throws SQLException {
        String query = "SELECT * FROM alerts WHERE alert_id = ?";
        try (PreparedStatement stmt = connection.prepareStatement(query)) {
            stmt.setInt(1, alertId);
            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    return mapResultSetToAlert(rs);
                }
            }
        }
        return null;
    }

    @Override
    public void updateAlert(Alert alert) throws SQLException {
        String query = "UPDATE alerts SET type = ?, vehicle_id = ?, message = ?, timestamp = ?, status = ? WHERE alert_id = ?";
        try (PreparedStatement stmt = connection.prepareStatement(query)) {
            stmt.setString(1, alert.getType());
            stmt.setString(2, alert.getVehicleId());
            stmt.setString(3, alert.getMessage());
            stmt.setString(4, alert.getTimestamp());
            stmt.setString(5, alert.getStatus());
            stmt.setInt(6, alert.getAlertId());
            stmt.executeUpdate();
        }
    }

    @Override
    public List<Alert> getFilteredAlerts(String type, String status, String vehicleId) throws SQLException {
        List<Alert> alerts = new ArrayList<>();
        StringBuilder query = new StringBuilder("SELECT * FROM alerts WHERE 1=1");
        List<String> params = new ArrayList<>();

        if (type != null && !type.isEmpty()) {
            query.append(" AND type = ?");
            params.add(type);
        }
        if (status != null && !status.isEmpty()) {
            query.append(" AND status = ?");
            params.add(status);
        }
        if (vehicleId != null && !vehicleId.isEmpty()) {
            query.append(" AND vehicle_id = ?");
            params.add(vehicleId);
        }

        try (PreparedStatement stmt = connection.prepareStatement(query.toString())) {
            for (int i = 0; i < params.size(); i++) {
                stmt.setString(i + 1, params.get(i));
            }
            try (ResultSet rs = stmt.executeQuery()) {
                while (rs.next()) {
                    alerts.add(mapResultSetToAlert(rs));
                }
            }
        }
        return alerts;
    }

    @Override
    public List<Alert> getAlertsByVehicleAndType(String vehicleId, String type) throws SQLException {
        List<Alert> alerts = new ArrayList<>();
        String query = "SELECT * FROM alerts WHERE vehicle_id = ? AND type = ?";
        try (PreparedStatement stmt = connection.prepareStatement(query)) {
            stmt.setString(1, vehicleId);
            stmt.setString(2, type);
            try (ResultSet rs = stmt.executeQuery()) {
                while (rs.next()) {
                    alerts.add(mapResultSetToAlert(rs));
                }
            }
        }
        return alerts;
    }

    /**
     * Maps the current row of a ResultSet to an Alert object.
     * @param rs the ResultSet to map
     * @return a populated Alert object
     * @throws SQLException if column access fails
     */
    private Alert mapResultSetToAlert(ResultSet rs) throws SQLException {
        Alert alert = new Alert();
        alert.setAlertId(rs.getInt("alert_id"));
        alert.setType(rs.getString("type"));
        alert.setVehicleId(rs.getString("vehicle_id"));
        alert.setMessage(rs.getString("message"));
        alert.setTimestamp(rs.getString("timestamp"));
        alert.setStatus(rs.getString("status"));
        return alert;
    }
}
