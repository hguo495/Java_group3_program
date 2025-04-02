package businesslayer;

import dataaccesslayer.MaintenanceTaskDAO;
import dataaccesslayer.MaintenanceTaskDAOImpl;
import entity.MaintenanceTask;
import transferobjects.CredentialsDTO;
import java.sql.SQLException;
import java.util.List;

/**
 * Business logic for maintenance operations using Command pattern.
 * @author Hongchen Guo
 */
public class MaintenanceBusinessLogic {
    private MaintenanceTaskDAO maintenanceTaskDAO;

    /**
     * Constructor that initializes the DAO.
     * @param creds the credentials to connect to the database
     * @throws SQLException if a database error occurs
     */
    public MaintenanceBusinessLogic(CredentialsDTO creds) throws SQLException {
        this.maintenanceTaskDAO = new MaintenanceTaskDAOImpl(creds);
    }

    /**
     * Gets all maintenance tasks.
     * @return a list of all maintenance tasks
     * @throws SQLException if a database error occurs
     */
    public List<MaintenanceTask> getAllMaintenanceTasks() throws SQLException {
        return maintenanceTaskDAO.getAllMaintenanceTasks();
    }

    /**
     * Schedules a maintenance task using the Command pattern.
     * @param task the maintenance task to schedule
     * @throws SQLException if a database error occurs
     */
    public void scheduleMaintenanceTask(MaintenanceTask task) throws SQLException {
        Command command = new ScheduleMaintenanceCommand(maintenanceTaskDAO, task);
        command.execute();
    }
}