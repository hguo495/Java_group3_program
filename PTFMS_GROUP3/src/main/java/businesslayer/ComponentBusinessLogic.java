package businesslayer;

import dataaccesslayer.ComponentDAO;
import dataaccesslayer.ComponentDAOImpl;
import entity.Component;
import transferobjects.CredentialsDTO;
import java.sql.SQLException;
import java.util.List;

/**
 * Business logic for component operations with maintenance alert trigger.
 * @author Hongchen Guo
 */
public class ComponentBusinessLogic {
    private ComponentDAO componentDAO;

    /**
     * Constructor that initializes the DAO.
     * @param creds the credentials to connect to the database
     * @throws SQLException if a database error occurs
     */
    public ComponentBusinessLogic(CredentialsDTO creds) throws SQLException {
        this.componentDAO = new ComponentDAOImpl(creds);
    }

    /**
     * Gets all components.
     * @return a list of all components
     * @throws SQLException if a database error occurs
     */
    public List<Component> getAllComponents() throws SQLException {
        return componentDAO.getAllComponents();
    }

    /**
     * Adds a new component and checks for maintenance alerts.
     * @param component the component to add
     * @throws SQLException if a database error occurs
     */
    public void addComponent(Component component) throws SQLException {
        componentDAO.addComponent(component);
        checkComponentForAlert(component);
    }

    /**
     * Checks if the component triggers a maintenance alert.
     * @param component the component to check
     * @throws SQLException if a database error occurs
     */
    private void checkComponentForAlert(Component component) throws SQLException {
        // Customizable thresholds
        double maxHoursThreshold = 10000.0;
        double maxWearThreshold = 80.0;

        if (component.getHoursUsed() >= maxHoursThreshold || component.getWearPercentage() >= maxWearThreshold) {
            AlertBusinessLogic alertLogic = new AlertBusinessLogic(new CredentialsDTO("root", "1234"));
            String message = "Component ID " + component.getComponentId() + " on vehicle " + component.getVehicleId() + " requires maintenance.";
            alertLogic.addAlert("Maintenance", component.getVehicleId(), message);
        }
    }
}
