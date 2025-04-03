package dataaccesslayer;

import entity.EnergyUsage;
import transferobjects.CredentialsDTO;
import java.sql.*;
import java.util.*;

public class EnergyUsageDAOImpl implements EnergyUsageDAO {
    private final Connection connection;

    public EnergyUsageDAOImpl(CredentialsDTO creds) throws SQLException {
        DataSource dataSource = DataSource.getInstance(creds);
        this.connection = dataSource.getConnection();
    }

    @Override
public List<EnergyUsage> getFilteredEnergyUsage(String facilityId, String energyType, String startDate, String endDate) throws SQLException {
    List<EnergyUsage> usages = new ArrayList<>();
    StringBuilder query = new StringBuilder("SELECT * FROM energy_usage WHERE 1=1");

    if (facilityId != null && !facilityId.isEmpty()) {
        query.append(" AND vehicle_id = ?");
    }
    if (energyType != null && !energyType.isEmpty()) {
        query.append(" AND fuel_energy_type = ?");
    }
    if (startDate != null && !startDate.isEmpty()) {
        query.append(" AND timestamp >= ?");
    }
    if (endDate != null && !endDate.isEmpty()) {
        query.append(" AND timestamp <= ?");
    }

    try (PreparedStatement stmt = connection.prepareStatement(query.toString())) {
        int index = 1;
        if (facilityId != null && !facilityId.isEmpty()) {
            stmt.setString(index++, facilityId);
        }
        if (energyType != null && !energyType.isEmpty()) {
            stmt.setString(index++, energyType);
        }
        if (startDate != null && !startDate.isEmpty()) {
            stmt.setString(index++, startDate + " 00:00:00");
        }
        if (endDate != null && !endDate.isEmpty()) {
            stmt.setString(index++, endDate + " 23:59:59");
        }

        try (ResultSet rs = stmt.executeQuery()) {
            while (rs.next()) {
                EnergyUsage usage = new EnergyUsage();
                usage.setUsageId(rs.getInt("usage_id"));
                usage.setVehicleId(rs.getString("vehicle_id"));
                usage.setFuelEnergyType(rs.getString("fuel_energy_type"));
                usage.setAmountUsed(rs.getDouble("amount_used"));
                usage.setDistanceTraveled(rs.getDouble("distance_traveled"));
                usage.setTimestamp(rs.getString("timestamp"));
                usages.add(usage);
            }
        }
    }

    return usages;
}


    @Override
    public void addEnergyUsage(EnergyUsage usage) throws SQLException {
        String query = "INSERT INTO energy_usage (vehicle_id, fuel_energy_type, amount_used, distance_traveled, timestamp) VALUES (?, ?, ?, ?, ?)";
        try (PreparedStatement stmt = connection.prepareStatement(query)) {
            stmt.setString(1, usage.getVehicleId());
            stmt.setString(2, usage.getFuelEnergyType());
            stmt.setDouble(3, usage.getAmountUsed());
            stmt.setDouble(4, usage.getDistanceTraveled());
            stmt.setString(5, usage.getTimestamp());
            stmt.executeUpdate();
        }
    }

    @Override
    public EnergyUsage getEnergyUsageById(int usageId) throws SQLException {
        String query = "SELECT * FROM energy_usage WHERE usage_id = ?";
        try (PreparedStatement stmt = connection.prepareStatement(query)) {
            stmt.setInt(1, usageId);
            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    EnergyUsage usage = new EnergyUsage();
                    usage.setUsageId(rs.getInt("usage_id"));
                    usage.setVehicleId(rs.getString("vehicle_id"));
                    usage.setFuelEnergyType(rs.getString("fuel_energy_type"));
                    usage.setAmountUsed(rs.getDouble("amount_used"));
                    usage.setDistanceTraveled(rs.getDouble("distance_traveled"));
                    usage.setTimestamp(rs.getString("timestamp"));
                    return usage;
                }
            }
        }
        return null;
    }

    @Override
    public void updateEnergyUsage(EnergyUsage usage) throws SQLException {
        String query = "UPDATE energy_usage SET vehicle_id=?, fuel_energy_type=?, amount_used=?, distance_traveled=?, timestamp=? WHERE usage_id=?";
        try (PreparedStatement stmt = connection.prepareStatement(query)) {
            stmt.setString(1, usage.getVehicleId());
            stmt.setString(2, usage.getFuelEnergyType());
            stmt.setDouble(3, usage.getAmountUsed());
            stmt.setDouble(4, usage.getDistanceTraveled());
            stmt.setString(5, usage.getTimestamp());
            stmt.setInt(6, usage.getUsageId());
            stmt.executeUpdate();
        }
    }

    @Override
    public void deleteEnergyUsage(int usageId) throws SQLException {
        String query = "DELETE FROM energy_usage WHERE usage_id = ?";
        try (PreparedStatement stmt = connection.prepareStatement(query)) {
            stmt.setInt(1, usageId);
            stmt.executeUpdate();
        }
    }
}