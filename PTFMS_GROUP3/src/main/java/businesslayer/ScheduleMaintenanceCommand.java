package businesslayer;

import dataaccesslayer.MaintenanceTaskDAO;
import entity.MaintenanceTask;
import java.sql.SQLException;

/**
 * Command to schedule a maintenance task.
 * @author Hongchen Guo
 * @modifiedby Mei
 */
public class ScheduleMaintenanceCommand implements Command {
    private MaintenanceTaskDAO maintenanceTaskDAO;
    private MaintenanceTask task;

    /**
     * Constructor that initializes the DAO and task.
     * @param maintenanceTaskDAO the DAO for maintenance tasks
     * @param task the maintenance task to schedule
     */
    public ScheduleMaintenanceCommand(MaintenanceTaskDAO maintenanceTaskDAO, MaintenanceTask task) {
        this.maintenanceTaskDAO = maintenanceTaskDAO;
        this.task = task;
    }

    /**
     * Executes the command to schedule the task.
     * @throws SQLException if a database error occurs
     */
    @Override
    public void execute() throws SQLException {
        maintenanceTaskDAO.addMaintenanceTask(task);
    }
}