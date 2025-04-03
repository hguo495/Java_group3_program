package dataaccesslayer;

import entity.EnergyUsage;
import java.sql.SQLException;
import java.util.List;

public interface EnergyUsageDAO {
    List<EnergyUsage> getFilteredEnergyUsage(String facilityId, String energyType, String startDate, String endDate) throws SQLException;

    void addEnergyUsage(EnergyUsage usage) throws SQLException;
    EnergyUsage getEnergyUsageById(int usageId) throws SQLException;
    void updateEnergyUsage(EnergyUsage usage) throws SQLException;
    void deleteEnergyUsage(int usageId) throws SQLException;
}
