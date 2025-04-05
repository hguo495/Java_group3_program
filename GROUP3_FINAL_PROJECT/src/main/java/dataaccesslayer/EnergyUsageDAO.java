package dataaccesslayer;

import entity.EnergyUsage;
import java.sql.SQLException;
import java.util.List;

/**
 * DAO interface for energy usage operations.
 * @author Jinze Li
 */
public interface EnergyUsageDAO {
    List<EnergyUsage> getAllEnergyUsage() throws SQLException;
    EnergyUsage getEnergyUsageById(int id) throws SQLException;
    List<EnergyUsage> getFilteredEnergyUsage(String fuelEnergyType, String startDate, String endDate) throws SQLException;
    void addEnergyUsage(EnergyUsage usage) throws SQLException;
    void updateEnergyUsage(EnergyUsage usage) throws SQLException;
    void deleteEnergyUsage(int id) throws SQLException;
}