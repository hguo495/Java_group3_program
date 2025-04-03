package dataaccesslayer;

import entity.Alert;
import transferobjects.CredentialsDTO;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**
 * DAO implementation for alert operations.
 * @author Jinze Li
 */
public class AlertDAOImpl implements AlertDAO {
    private final Connection connection;

    /**
     * Constructor that initializes the database connection.
     * @param creds the credentials to connect to the database
     * @throws SQLException if a database error occurs
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
                Alert alert = new Alert();
                alert.setAlertId(rs.getInt("alert_id"));
                alert.setType(rs.getString("type"));
                alert.setVehicleId(rs.getString("vehicle_id"));
                alert.setMessage(rs.getString("message"));
                alert.setTimestamp(rs.getString("timestamp"));
                alert.setStatus(rs.getString("status"));
                alerts.add(alert);
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
}