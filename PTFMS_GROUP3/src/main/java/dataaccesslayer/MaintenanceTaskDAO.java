package dataaccesslayer;

import entity.MaintenanceTask;
import java.sql.SQLException;
import java.util.List;

/**
 * DAO interface for maintenance task operations.
 * @author Jinze Li
 */
public interface MaintenanceTaskDAO {
    List<MaintenanceTask> getAllMaintenanceTasks() throws SQLException;
    void addMaintenanceTask(MaintenanceTask task) throws SQLException;
}