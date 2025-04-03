package dataaccesslayer;

import entity.MaintenanceTask;
import transferobjects.CredentialsDTO;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**
 * DAO implementation for maintenance task operations.
 * @author Jinze Li
 */
public class MaintenanceTaskDAOImpl implements MaintenanceTaskDAO {
    private final Connection connection;

    /**
     * Constructor that initializes the database connection.
     * @param creds the credentials to connect to the database
     * @throws SQLException if a database error occurs
     */
    public MaintenanceTaskDAOImpl(CredentialsDTO creds) throws SQLException {
        DataSource dataSource = DataSource.getInstance(creds);
        this.connection = dataSource.getConnection();
    }

    @Override
    public List<MaintenanceTask> getAllMaintenanceTasks() throws SQLException {
        List<MaintenanceTask> tasks = new ArrayList<>();
        String query = "SELECT * FROM maintenance_tasks";
        try (PreparedStatement stmt = connection.prepareStatement(query);
             ResultSet rs = stmt.executeQuery()) {
            while (rs.next()) {
                MaintenanceTask task = new MaintenanceTask();
                task.setTaskId(rs.getInt("task_id"));
                task.setComponentId(rs.getInt("component_id"));
                task.setVehicleId(rs.getString("vehicle_id"));
                task.setDescription(rs.getString("description"));
                task.setScheduledDate(rs.getString("scheduled_date"));
                task.setStatus(rs.getString("status"));
                task.setTechnicianId(rs.getInt("technician_id") == 0 ? null : rs.getInt("technician_id"));
                tasks.add(task);
            }
        }
        return tasks;
    }

    @Override
    public void addMaintenanceTask(MaintenanceTask task) throws SQLException {
        String query = "INSERT INTO maintenance_tasks (component_id, vehicle_id, description, scheduled_date, status, technician_id) VALUES (?, ?, ?, ?, ?, ?)";
        try (PreparedStatement stmt = connection.prepareStatement(query)) {
            stmt.setInt(1, task.getComponentId());
            stmt.setString(2, task.getVehicleId());
            stmt.setString(3, task.getDescription());
            stmt.setString(4, task.getScheduledDate());
            stmt.setString(5, task.getStatus());
            if (task.getTechnicianId() == null) {
                stmt.setNull(6, java.sql.Types.INTEGER);
            } else {
                stmt.setInt(6, task.getTechnicianId());
            }
            stmt.executeUpdate();
        }
    }
}