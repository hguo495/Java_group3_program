package dataaccesslayer;

import entity.Alert;
import java.sql.SQLException;
import java.util.List;

/**
 * DAO interface for alert operations.
 * @author Jinze Li
 */
public interface AlertDAO {
    List<Alert> getAllAlerts() throws SQLException;
    void addAlert(Alert alert) throws SQLException;
    Alert getAlertById(int alertId) throws SQLException;
    void updateAlert(Alert alert) throws SQLException;
    List<Alert> getFilteredAlerts(String type, String status, String vehicleId) throws SQLException;
}