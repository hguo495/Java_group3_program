package dataaccesslayer;

import entity.Alert;
import java.sql.SQLException;
import java.util.List;

/**
 * DAO interface for alert operations.
 * Extended with a method to check for duplicate alerts before insertion.
 * 
 * @author Jinze Li
 */
public interface AlertDAO {
    List<Alert> getAllAlerts() throws SQLException;

    void addAlert(Alert alert) throws SQLException;

    Alert getAlertById(int alertId) throws SQLException;

    void updateAlert(Alert alert) throws SQLException;

    List<Alert> getFilteredAlerts(String type, String status, String vehicleId) throws SQLException;

    /**
     * Finds alerts by vehicle ID and type.
     * Used to check for duplicate alert before insertion.
     *
     * @param vehicleId the vehicle identifier
     * @param type the alert type (e.g. Maintenance, Energy, Fuel)
     * @return list of matching alerts
     * @throws SQLException if database access fails
     */
    List<Alert> getAlertsByVehicleAndType(String vehicleId, String type) throws SQLException;

    /**
     * Adds an alert only if it does not already exist with the same vehicle, type, and message.
     *
     * @param alert the alert to be added
     * @throws SQLException if a database access error occurs
     */
    default void addAlertIfNotExists(Alert alert) throws SQLException {
        List<Alert> existingAlerts = getAlertsByVehicleAndType(alert.getVehicleId(), alert.getType());
        boolean alreadyExists = existingAlerts.stream()
                .anyMatch(a -> a.getMessage().equals(alert.getMessage()));

        if (!alreadyExists) {
            addAlert(alert);
        }
    }
}
