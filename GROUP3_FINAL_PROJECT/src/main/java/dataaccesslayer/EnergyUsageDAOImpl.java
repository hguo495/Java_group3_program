package dataaccesslayer;

import entity.EnergyUsage;
import transferobjects.CredentialsDTO;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**
 * DAO implementation for energy usage operations.
 * @author Jinze Li
 */
public class EnergyUsageDAOImpl implements EnergyUsageDAO {
    private final Connection connection;

    /**
     * Constructor that initializes the database connection.
     * @param creds the credentials to connect to the database
     * @throws SQLException if a database error occurs
     */
    public EnergyUsageDAOImpl(CredentialsDTO creds) throws SQLException {
        DataSource dataSource = DataSource.getInstance(creds);
        this.connection = dataSource.getConnection();
    }

    @Override
    public List<EnergyUsage> getAllEnergyUsage() throws SQLException {
        List<EnergyUsage> usages = new ArrayList<>();
        String query = "SELECT * FROM energy_usage";
        try (PreparedStatement stmt = connection.prepareStatement(query);
             ResultSet rs = stmt.executeQuery()) {
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
        return usages;
    }

    @Override
    public EnergyUsage getEnergyUsageById(int id) throws SQLException {
        EnergyUsage usage = null;
        String query = "SELECT * FROM energy_usage WHERE usage_id = ?";
        try (PreparedStatement stmt = connection.prepareStatement(query)) {
            stmt.setInt(1, id);
            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    usage = new EnergyUsage();
                    usage.setUsageId(rs.getInt("usage_id"));
                    usage.setVehicleId(rs.getString("vehicle_id"));
                    usage.setFuelEnergyType(rs.getString("fuel_energy_type"));
                    usage.setAmountUsed(rs.getDouble("amount_used"));
                    usage.setDistanceTraveled(rs.getDouble("distance_traveled"));
                    usage.setTimestamp(rs.getString("timestamp"));
                }
            }
        }
        return usage;
    }

    @Override
    public List<EnergyUsage> getFilteredEnergyUsage(String fuelEnergyType, String startDate, String endDate) throws SQLException {
        List<EnergyUsage> usages = new ArrayList<>();
        StringBuilder query = new StringBuilder("SELECT * FROM energy_usage WHERE 1=1");
        List<Object> params = new ArrayList<>();

        if (fuelEnergyType != null && !fuelEnergyType.isEmpty()) {
            query.append(" AND fuel_energy_type = ?");
            params.add(fuelEnergyType);
        }
        if (startDate != null && !startDate.isEmpty()) {
            query.append(" AND timestamp >= ?");
            params.add(startDate);
        }
        if (endDate != null && !endDate.isEmpty()) {
            query.append(" AND timestamp <= ?");
            params.add(endDate);
        }

        try (PreparedStatement stmt = connection.prepareStatement(query.toString())) {
            for (int i = 0; i < params.size(); i++) {
                stmt.setObject(i + 1, params.get(i));
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
    public void updateEnergyUsage(EnergyUsage usage) throws SQLException {
        String query = "UPDATE energy_usage SET vehicle_id = ?, fuel_energy_type = ?, amount_used = ?, distance_traveled = ?, timestamp = ? WHERE usage_id = ?";
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
    public void deleteEnergyUsage(int id) throws SQLException {
        String query = "DELETE FROM energy_usage WHERE usage_id = ?";
        try (PreparedStatement stmt = connection.prepareStatement(query)) {
            stmt.setInt(1, id);
            stmt.executeUpdate();
        }
    }
}