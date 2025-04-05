package dataaccesslayer;

import entity.Vehicle;
import transferobjects.CredentialsDTO;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**
 * DAO implementation for vehicle operations.
 * @author Jinze Li
 */
public class VehicleDAOImpl implements VehicleDAO {
    private final Connection connection;

    /**
     * Constructor that initializes the database connection.
     * @param creds the credentials to connect to the database
     * @throws SQLException if a database error occurs
     */
    public VehicleDAOImpl(CredentialsDTO creds) throws SQLException {
        DataSource dataSource = DataSource.getInstance(creds);
        this.connection = dataSource.getConnection();
    }

    @Override
    public List<Vehicle> getAllVehicles() throws SQLException {
        List<Vehicle> vehicles = new ArrayList<>();
        String query = "SELECT * FROM vehicles";
        try (PreparedStatement stmt = connection.prepareStatement(query);
             ResultSet rs = stmt.executeQuery()) {
            while (rs.next()) {
                Vehicle vehicle = new Vehicle();
                vehicle.setVehicleId(rs.getString("vehicle_id"));
                vehicle.setType(rs.getString("type"));
                vehicle.setNumber(rs.getString("number"));
                vehicle.setFuelType(rs.getString("fuel_type"));
                vehicle.setConsumptionRate(rs.getDouble("consumption_rate"));
                vehicle.setMaxPassengers(rs.getInt("max_passengers"));
                vehicle.setRoute(rs.getString("route"));
                vehicle.setStatus(rs.getString("status"));
                vehicles.add(vehicle);
            }
        }
        return vehicles;
    }

    @Override
    public Vehicle getVehicleById(String vehicleId) throws SQLException {
        String query = "SELECT * FROM vehicles WHERE vehicle_id = ?";
        try (PreparedStatement stmt = connection.prepareStatement(query)) {
            stmt.setString(1, vehicleId);
            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    Vehicle vehicle = new Vehicle();
                    vehicle.setVehicleId(rs.getString("vehicle_id"));
                    vehicle.setType(rs.getString("type"));
                    vehicle.setNumber(rs.getString("number"));
                    vehicle.setFuelType(rs.getString("fuel_type"));
                    vehicle.setConsumptionRate(rs.getDouble("consumption_rate"));
                    vehicle.setMaxPassengers(rs.getInt("max_passengers"));
                    vehicle.setRoute(rs.getString("route"));
                    vehicle.setStatus(rs.getString("status"));
                    return vehicle;
                }
            }
        }
        return null;
    }

    @Override
    public void addVehicle(Vehicle vehicle) throws SQLException {
        String query = "INSERT INTO vehicles (vehicle_id, type, number, fuel_type, consumption_rate, max_passengers, route, status) VALUES (?, ?, ?, ?, ?, ?, ?, ?)";
        try (PreparedStatement stmt = connection.prepareStatement(query)) {
            stmt.setString(1, vehicle.getVehicleId());
            stmt.setString(2, vehicle.getType());
            stmt.setString(3, vehicle.getNumber());
            stmt.setString(4, vehicle.getFuelType());
            stmt.setDouble(5, vehicle.getConsumptionRate());
            stmt.setInt(6, vehicle.getMaxPassengers());
            stmt.setString(7, vehicle.getRoute());
            stmt.setString(8, vehicle.getStatus());
            stmt.executeUpdate();
        }
    }

    @Override
    public void updateVehicle(Vehicle vehicle) throws SQLException {
        String query = "UPDATE vehicles SET type = ?, number = ?, fuel_type = ?, consumption_rate = ?, max_passengers = ?, route = ?, status = ? WHERE vehicle_id = ?";
        try (PreparedStatement stmt = connection.prepareStatement(query)) {
            stmt.setString(1, vehicle.getType());
            stmt.setString(2, vehicle.getNumber());
            stmt.setString(3, vehicle.getFuelType());
            stmt.setDouble(4, vehicle.getConsumptionRate());
            stmt.setInt(5, vehicle.getMaxPassengers());
            stmt.setString(6, vehicle.getRoute());
            stmt.setString(7, vehicle.getStatus());
            stmt.setString(8, vehicle.getVehicleId());
            int rowsAffected = stmt.executeUpdate();
            if (rowsAffected == 0) {
                throw new SQLException("Vehicle with ID " + vehicle.getVehicleId() + " not found.");
            }
        }
    }

    @Override
    public void deleteVehicle(String vehicleId) throws SQLException {
        String query = "DELETE FROM vehicles WHERE vehicle_id = ?";
        try (PreparedStatement stmt = connection.prepareStatement(query)) {
            stmt.setString(1, vehicleId);
            int rowsAffected = stmt.executeUpdate();
            if (rowsAffected == 0) {
                throw new SQLException("Vehicle with ID " + vehicleId + " not found.");
            }
        }
    }
}