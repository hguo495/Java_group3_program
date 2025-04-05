package dataaccesslayer;

import entity.OperatorLog;
import transferobjects.CredentialsDTO;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**
 * DAO implementation for operator log operations.
 * @author Jinze Li
 */
public class OperatorLogDAOImpl implements OperatorLogDAO {
    private final Connection connection;

    /**
     * Constructor that initializes the database connection.
     * @param creds the credentials to connect to the database
     * @throws SQLException if a database error occurs
     */
    public OperatorLogDAOImpl(CredentialsDTO creds) throws SQLException {
        DataSource dataSource = DataSource.getInstance(creds);
        this.connection = dataSource.getConnection();
    }

    @Override
    public List<OperatorLog> getAllOperatorLogs() throws SQLException {
        List<OperatorLog> logs = new ArrayList<>();
        String query = "SELECT * FROM operator_logs";
        try (PreparedStatement stmt = connection.prepareStatement(query);
             ResultSet rs = stmt.executeQuery()) {
            while (rs.next()) {
                OperatorLog log = new OperatorLog();
                log.setLogId(rs.getInt("log_id"));
                log.setVehicleId(rs.getString("vehicle_id"));
                log.setStartTime(rs.getString("start_time"));
                log.setEndTime(rs.getString("end_time"));
                log.setEventType(rs.getString("event_type"));
                log.setOperatorId(rs.getInt("operator_id"));
                logs.add(log);
            }
        }
        return logs;
    }

    @Override
    public void addOperatorLog(OperatorLog log) throws SQLException {
        String query = "INSERT INTO operator_logs (vehicle_id, start_time, end_time, event_type, operator_id) VALUES (?, ?, ?, ?, ?)";
        try (PreparedStatement stmt = connection.prepareStatement(query)) {
            stmt.setString(1, log.getVehicleId());
            stmt.setString(2, log.getStartTime());
            stmt.setString(3, log.getEndTime());
            stmt.setString(4, log.getEventType());
            stmt.setInt(5, log.getOperatorId());
            stmt.executeUpdate();
        }
    }
}