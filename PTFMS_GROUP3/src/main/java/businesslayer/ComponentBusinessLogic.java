package businesslayer;

import dataaccesslayer.ComponentDAO;
import dataaccesslayer.ComponentDAOImpl;
import entity.Component;
import transferobjects.CredentialsDTO;
import java.sql.SQLException;
import java.util.List;

/**
 * Business logic for component operations with maintenance alerts.
 * @author Hongchen Guo (Updated with component age alerts)
 */
public class ComponentBusinessLogic {
    private ComponentDAO componentDAO;
    private MaintenanceAlertService alertService;
    private CredentialsDTO creds;

    /**
     * Constructor that initializes the DAO and alert service.
     * @param creds the credentials to connect to the database
     * @throws SQLException if a database error occurs
     */
    public ComponentBusinessLogic(CredentialsDTO creds) throws SQLException {
        this.componentDAO = new ComponentDAOImpl(creds);
        this.creds = creds;
        this.alertService = new MaintenanceAlertService(creds);
    }

    /**
     * Gets all components.
     * @return a list of all components
     * @throws SQLException if a database error occurs
     */
    public List<Component> getAllComponents() throws SQLException {
        List<Component> components = componentDAO.getAllComponents();
        
        // Trigger check for components needing maintenance
        alertService.checkComponentsForMaintenance(components);
        
        return components;
    }
    
    /**
     * Gets a component by ID.
     * @param componentId the ID of the component to retrieve
     * @return the component, or null if not found
     * @throws SQLException if a database error occurs
     */
    public Component getComponentById(int componentId) throws SQLException {
        return componentDAO.getComponentById(componentId);
    }

    /**
     * Adds a new component and checks if it immediately needs maintenance.
     * @param component the component to add
     * @throws SQLException if a database error occurs
     */
    public void addComponent(Component component) throws SQLException {
        componentDAO.addComponent(component);
        
        // After adding a component, check if it needs immediate maintenance
        alertService.checkComponentForMaintenance(component);
    }
    
    /**
     * Updates a component's information.
     * @param component the component to update
     * @throws SQLException if a database error occurs
     */
    public void updateComponent(Component component) throws SQLException {
        // First get the old component data for comparison
        Component oldComponent = componentDAO.getComponentById(component.getComponentId());
        
        // Update the component in the database
        componentDAO.updateComponent(component);
        
        // Check if maintenance needs have changed
        boolean wasNeedingMaintenance = (oldComponent != null) && 
                                        (oldComponent.getWearPercentage() > 75.0 || 
                                         oldComponent.getHoursUsed() > 1000.0);
        
        boolean nowNeedsMaintenance = alertService.checkComponentForMaintenance(component);
        
        // Alert already created by checkComponentForMaintenance if needed
    }
}