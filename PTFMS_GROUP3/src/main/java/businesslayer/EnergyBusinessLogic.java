package businesslayer;

import dataaccesslayer.EnergyUsageDAO;
import dataaccesslayer.EnergyUsageDAOImpl;
import entity.EnergyUsage;
import transferobjects.CredentialsDTO;
import java.sql.SQLException;
import java.util.List;

/**
 * Business logic for energy usage operations using Strategy pattern.
 * @author Hongchen Guo
 */
public class EnergyBusinessLogic {
    private EnergyUsageDAO energyUsageDAO;
    private EnergyStrategy energyStrategy;

    /**
     * Constructor that initializes the DAO.
     * @param creds the credentials to connect to the database
     * @throws SQLException if a database error occurs
     */
    public EnergyBusinessLogic(CredentialsDTO creds) throws SQLException {
        this.energyUsageDAO = new EnergyUsageDAOImpl(creds);
    }

    /**
     * Gets all energy usage records.
     * @return a list of all energy usage records
     * @throws SQLException if a database error occurs
     */
    public List<EnergyUsage> getAllEnergyUsage() throws SQLException {
        return energyUsageDAO.getAllEnergyUsage();
    }

    /**
     * Gets an energy usage record by ID.
     * @param id the ID of the record
     * @return the energy usage record
     * @throws SQLException if a database error occurs
     */
    public EnergyUsage getEnergyUsageById(int id) throws SQLException {
        return energyUsageDAO.getEnergyUsageById(id);
    }

    /**
     * Gets energy usage records based on filters.
     * @param fuelEnergyType the fuel/energy type filter
     * @param startDate the start date filter
     * @param endDate the end date filter
     * @return a list of filtered energy usage records
     * @throws SQLException if a database error occurs
     */
    public List<EnergyUsage> getFilteredEnergyUsage(String fuelEnergyType, String startDate, String endDate) throws SQLException {
        return energyUsageDAO.getFilteredEnergyUsage(fuelEnergyType, startDate, endDate);
    }

    /**
     * Adds a new energy usage record and checks efficiency.
     * @param usage the energy usage record to add
     * @throws SQLException if a database error occurs
     */
    public void addEnergyUsage(EnergyUsage usage) throws SQLException {
        energyUsageDAO.addEnergyUsage(usage);
        setStrategy(usage.getFuelEnergyType());

        if (!energyStrategy.isEfficient(usage.getAmountUsed(), usage.getDistanceTraveled())) {
            CredentialsDTO creds = new CredentialsDTO();
            creds.setUsername("root");
            creds.setPassword("1234");
            AlertBusinessLogic alertLogic = new AlertBusinessLogic(creds);
            alertLogic.addAlert("Fuel", usage.getVehicleId(), "Excessive fuel consumption detected");
        }
    }

    /**
     * Updates an existing energy usage record.
     * @param usage the updated energy usage record
     * @throws SQLException if a database error occurs
     */
    public void updateEnergyUsage(EnergyUsage usage) throws SQLException {
        energyUsageDAO.updateEnergyUsage(usage);
        setStrategy(usage.getFuelEnergyType());

        if (!energyStrategy.isEfficient(usage.getAmountUsed(), usage.getDistanceTraveled())) {
            CredentialsDTO creds = new CredentialsDTO();
            creds.setUsername("root");
            creds.setPassword("1234");
            AlertBusinessLogic alertLogic = new AlertBusinessLogic(creds);
            alertLogic.addAlert("Fuel", usage.getVehicleId(), "Excessive fuel consumption detected");
        }
    }

    /**
     * Deletes an energy usage record by ID.
     * @param id the ID of the record to delete
     * @throws SQLException if a database error occurs
     */
    public void deleteEnergyUsage(int id) throws SQLException {
        energyUsageDAO.deleteEnergyUsage(id);
    }

    /**
     * Sets the energy strategy based on fuel type.
     * @param fuelType the fuel/energy type
     */
    private void setStrategy(String fuelType) {
        if ("Diesel".equalsIgnoreCase(fuelType) || "CNG".equalsIgnoreCase(fuelType)) {
            this.energyStrategy = new DieselStrategy();
        } else {
            this.energyStrategy = new ElectricStrategy();
        }
    }
}
