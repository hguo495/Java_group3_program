package dataaccesslayer;

import entity.MaintenanceTask;
import java.sql.SQLException;
import java.util.List;

/**
 * DAO interface for maintenance task operations.
 * @author Hongchen Guo
 */
public interface MaintenanceTaskDAO {
    List<MaintenanceTask> getAllMaintenanceTasks() throws SQLException;
    void addMaintenanceTask(MaintenanceTask task) throws SQLException;
    void updateMaintenanceTask(MaintenanceTask task) throws SQLException;
    void deleteMaintenanceTask(int taskId) throws SQLException;
}