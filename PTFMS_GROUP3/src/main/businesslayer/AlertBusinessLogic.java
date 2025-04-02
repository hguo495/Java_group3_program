package businesslayer;

import businesslayer.Observer;
import dataaccesslayer.AlertDAO;
import dataaccesslayer.AlertDAOImpl;
import entity.Alert;
import transferobjects.CredentialsDTO;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

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
        alert.setTimestamp(String.valueOf(System.currentTimeMillis()));
        alert.setStatus("Pending");
        alertDAO.addAlert(alert);
        notifyObservers();
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