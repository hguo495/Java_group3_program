package businesslayer;

import businesslayer.Observer;
import dataaccesslayer.AlertDAO;
import dataaccesslayer.AlertDAOImpl;
import entity.Alert;
import transferobjects.CredentialsDTO;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.text.SimpleDateFormat;
import java.util.Date;
/**
 * Business logic for alert operations using Observer pattern.
 * @author Hongchen Guo
 */
public class AlertBusinessLogic {
    private AlertDAO alertDAO;
    private List<Observer> observers;

    /**
     * Constructor that initializes the DAO and observers.
     * @param creds the credentials to connect to the database
     * @throws SQLException if a database error occurs
     */
    public AlertBusinessLogic(CredentialsDTO creds) throws SQLException {
        this.alertDAO = new AlertDAOImpl(creds);
        this.observers = new ArrayList<>();
    }

    /**
     * Gets all alerts.
     * @return a list of all alerts
     * @throws SQLException if a database error occurs
     */
    public List<Alert> getAllAlerts() throws SQLException {
        return alertDAO.getAllAlerts();
    }

    /**
     * Gets filtered alerts based on type, status, and vehicle ID.
     * @param type the type of alert (optional)
     * @param status the status of alert (optional)
     * @param vehicleId the vehicle ID (optional)
     * @return a list of filtered alerts
     * @throws SQLException if a database error occurs
     */
    public List<Alert> getFilteredAlerts(String type, String status, String vehicleId) throws SQLException {
        return alertDAO.getFilteredAlerts(type, status, vehicleId);
    }

    /**
     * Gets a specific alert by its ID.
     * @param alertId the ID of the alert to retrieve
     * @return the Alert object, or null if not found
     * @throws SQLException if a database error occurs
     */
    public Alert getAlertById(int alertId) throws SQLException {
        return alertDAO.getAlertById(alertId);
    }

    /**
     * Adds a new alert and notifies observers.
     * @param type the type of alert
     * @param vehicleId the vehicle ID
     * @param message the alert message
     * @throws SQLException if a database error occurs
     */
    public void addAlert(String type, String vehicleId, String message) throws SQLException {
        Alert alert = new Alert();
        alert.setType(type);
        alert.setVehicleId(vehicleId);
        alert.setMessage(message);
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String formattedDate = sdf.format(new Date());
        alert.setTimestamp(formattedDate);
        alert.setStatus("Pending");
        alertDAO.addAlert(alert);
        notifyObservers();
    }

    /**
     * Resolves an alert by updating its status to "Resolved" and notifies observers.
     * @param alertId the ID of the alert to resolve
     * @throws SQLException if a database error occurs
     */
    public void resolveAlert(int alertId) throws SQLException {
        Alert alert = alertDAO.getAlertById(alertId);
        if (alert == null) {
            throw new SQLException("Alert with ID " + alertId + " not found");
        }
        if (!"Pending".equalsIgnoreCase(alert.getStatus())) {
            throw new SQLException("Alert with ID " + alertId + " cannot be resolved because its status is " + alert.getStatus());
        }
        alert.setStatus("Resolved");
        alertDAO.updateAlert(alert);
        try {
            notifyObservers();
        } catch (Exception e) {
            System.err.println("Error notifying observers: " + e.getMessage());
        }
    }

    /**
     * Registers an observer.
     * @param observer the observer to register
     */
    public void registerObserver(Observer observer) {
        observers.add(observer);
    }

    /**
     * Notifies all registered observers.
     */
    private void notifyObservers() {
        for (Observer observer : observers) {
            observer.update();
        }
    }
}