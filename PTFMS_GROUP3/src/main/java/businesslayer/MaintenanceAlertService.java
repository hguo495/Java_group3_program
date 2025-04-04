package businesslayer;

import dataaccesslayer.AlertDAO;
import dataaccesslayer.AlertDAOImpl;
import entity.Alert;
import entity.Component;
import entity.EnergyUsage;
import transferobjects.CredentialsDTO;
import java.sql.SQLException;
import java.util.List;

/**
 * Business logic for maintenance alerts based on fuel levels and component age.
 * @author Modified Implementation
 */
public class MaintenanceAlertService {
    
    private AlertBusinessLogic alertLogic;
    private static final double LOW_FUEL_THRESHOLD = 5.0; // 5 liters/gallons for diesel
    private static final double LOW_BATTERY_THRESHOLD = 20.0; // 20 kWh for electric
    private static final double COMPONENT_HOURS_THRESHOLD = 1000.0; // 1000 hours
    private static final double COMPONENT_WEAR_THRESHOLD = 75.0; // 75% wear

    /**
     * Constructor that initializes the alert business logic.
     * @param creds the credentials to connect to the database
     * @throws SQLException if a database error occurs
     */
    public MaintenanceAlertService(CredentialsDTO creds) throws SQLException {
        this.alertLogic = new AlertBusinessLogic(creds);
        // Remove initialization of ComponentBusinessLogic to break circular dependency
    }
    
    /**
     * Checks if a fuel/energy alert should be created based on the latest usage data.
     * @param usage the energy usage record to check
     * @throws SQLException if a database error occurs
     */
    public void checkFuelLevelAlert(EnergyUsage usage) throws SQLException {
        String fuelType = usage.getFuelEnergyType();
        double amountUsed = usage.getAmountUsed();
        
        // Determine appropriate threshold based on fuel type
        double threshold = "Electricity".equalsIgnoreCase(fuelType) ? 
                LOW_BATTERY_THRESHOLD : LOW_FUEL_THRESHOLD;
        
        // If amount is below threshold, create an alert
        if (amountUsed < threshold) {
            String message = String.format("Low %s level detected: %.2f. Requires refueling/recharging.", 
                    fuelType, amountUsed);
            
            alertLogic.addAlert("Fuel", usage.getVehicleId(), message);
            System.out.println("Created fuel alert for vehicle: " + usage.getVehicleId());
        }
    }
    
    /**
     * Checks components and creates alerts for any that need maintenance.
     * @param components the list of components to check
     * @throws SQLException if a database error occurs
     */
    public void checkComponentsForMaintenance(List<Component> components) throws SQLException {
        for (Component component : components) {
            boolean needsMaintenance = false;
            String reason = "";
            
            // Check if component has been used beyond threshold hours
            if (component.getHoursUsed() > COMPONENT_HOURS_THRESHOLD) {
                needsMaintenance = true;
                reason = String.format("Component has been used for %.1f hours (threshold: %.1f hours)", 
                        component.getHoursUsed(), COMPONENT_HOURS_THRESHOLD);
            }
            
            // Check if component wear percentage is beyond threshold
            if (component.getWearPercentage() > COMPONENT_WEAR_THRESHOLD) {
                needsMaintenance = true;
                reason = String.format("%sComponent wear is at %.1f%% (threshold: %.1f%%)", 
                        needsMaintenance ? reason + ". Also, " : "", 
                        component.getWearPercentage(), COMPONENT_WEAR_THRESHOLD);
            }
            
            // Create alert if maintenance is needed
            if (needsMaintenance) {
                String message = String.format("Component %s of type '%s' needs maintenance. %s", 
                        component.getComponentId(), component.getType(), reason);
                
                alertLogic.addAlert("Maintenance", component.getVehicleId(), message);
                System.out.println("Created maintenance alert for component: " + component.getComponentId());
            }
        }
    }
    
    /**
     * Check a single component for maintenance needs.
     * @param component the component to check
     * @return true if maintenance is needed, false otherwise
     * @throws SQLException if a database error occurs
     */
    public boolean checkComponentForMaintenance(Component component) throws SQLException {
        boolean needsMaintenance = false;
        String reason = "";
        
        // Check if component has been used beyond threshold hours
        if (component.getHoursUsed() > COMPONENT_HOURS_THRESHOLD) {
            needsMaintenance = true;
            reason = String.format("Component has been used for %.1f hours (threshold: %.1f hours)", 
                    component.getHoursUsed(), COMPONENT_HOURS_THRESHOLD);
        }
        
        // Check if component wear percentage is beyond threshold
        if (component.getWearPercentage() > COMPONENT_WEAR_THRESHOLD) {
            needsMaintenance = true;
            reason = String.format("%sComponent wear is at %.1f%% (threshold: %.1f%%)", 
                    needsMaintenance ? reason + ". Also, " : "", 
                    component.getWearPercentage(), COMPONENT_WEAR_THRESHOLD);
        }
        
        // Create alert if maintenance is needed
        if (needsMaintenance) {
            String message = String.format("Component %s of type '%s' needs maintenance. %s", 
                    component.getComponentId(), component.getType(), reason);
            
            alertLogic.addAlert("Maintenance", component.getVehicleId(), message);
            System.out.println("Created maintenance alert for component: " + component.getComponentId());
        }
        
        return needsMaintenance;
    }
}