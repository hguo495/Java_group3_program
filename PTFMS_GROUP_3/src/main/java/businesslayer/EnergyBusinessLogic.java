package businesslayer;

import businesslayer.EnergyStrategy;
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
     * Adds a new energy usage record and checks efficiency.
     * @param usage the energy usage record to add
     * @throws SQLException if a database error occurs
     */
    public void addEnergyUsage(EnergyUsage usage) throws SQLException {
        energyUsageDAO.addEnergyUsage(usage);
        setStrategy(usage.getFuelEnergyType());
        if (!energyStrategy.isEfficient(usage.getAmountUsed(), usage.getDistanceTraveled())) {
            AlertBusinessLogic alertLogic = new AlertBusinessLogic(new CredentialsDTO());
            alertLogic.addAlert("Energy", usage.getVehicleId(), "Excessive energy usage detected");
        }
    }

    /**
     * Sets the energy strategy based on fuel type.
     * @param fuelType the fuel/energy type
     */
    private void setStrategy(String fuelType) {
        if ("Diesel".equals(fuelType) || "CNG".equals(fuelType)) {
            this.energyStrategy = new DieselStrategy();
        } else {
            this.energyStrategy = new ElectricStrategy();
        }
    }
}