package dataaccesslayer;

import entity.Component;
import transferobjects.CredentialsDTO;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**
 * Updated DAO implementation for component operations.
 * @author Jinze Li (Updated with updateComponent method)
 */
public class ComponentDAOImpl implements ComponentDAO {
    private final Connection connection;

    /**
     * Constructor that initializes the database connection.
     * @param creds the credentials to connect to the database
     * @throws SQLException if a database error occurs
     */
    public ComponentDAOImpl(CredentialsDTO creds) throws SQLException {
        DataSource dataSource = DataSource.getInstance(creds);
        this.connection = dataSource.getConnection();
    }

    @Override
    public List<Component> getAllComponents() throws SQLException {
        List<Component> components = new ArrayList<>();
        String query = "SELECT * FROM components";
        try (PreparedStatement stmt = connection.prepareStatement(query);
             ResultSet rs = stmt.executeQuery()) {
            while (rs.next()) {
                Component component = new Component();
                component.setComponentId(rs.getInt("component_id"));
                component.setVehicleId(rs.getString("vehicle_id"));
                component.setType(rs.getString("type"));
                component.setHoursUsed(rs.getDouble("hours_used"));
                component.setWearPercentage(rs.getDouble("wear_percentage"));
                component.setDiagnosticStatus(rs.getString("diagnostic_status"));
                components.add(component);
            }
        }
        return components;
    }

    @Override
    public void addComponent(Component component) throws SQLException {
        String query = "INSERT INTO components (vehicle_id, type, hours_used, wear_percentage, diagnostic_status) VALUES (?, ?, ?, ?, ?)";
        try (PreparedStatement stmt = connection.prepareStatement(query)) {
            stmt.setString(1, component.getVehicleId());
            stmt.setString(2, component.getType());
            stmt.setDouble(3, component.getHoursUsed());
            stmt.setDouble(4, component.getWearPercentage());
            stmt.setString(5, component.getDiagnosticStatus());
            stmt.executeUpdate();
        }
    }
    
    @Override
    public void updateComponent(Component component) throws SQLException {
        String query = "UPDATE components SET vehicle_id = ?, type = ?, hours_used = ?, wear_percentage = ?, diagnostic_status = ? WHERE component_id = ?";
        try (PreparedStatement stmt = connection.prepareStatement(query)) {
            stmt.setString(1, component.getVehicleId());
            stmt.setString(2, component.getType());
            stmt.setDouble(3, component.getHoursUsed());
            stmt.setDouble(4, component.getWearPercentage());
            stmt.setString(5, component.getDiagnosticStatus());
            stmt.setInt(6, component.getComponentId());
            int rowsAffected = stmt.executeUpdate();
            if (rowsAffected == 0) {
                throw new SQLException("No rows updated for component ID " + component.getComponentId());
            }
        }
    }
    
    @Override
    public Component getComponentById(int componentId) throws SQLException {
        String query = "SELECT * FROM components WHERE component_id = ?";
        try (PreparedStatement stmt = connection.prepareStatement(query)) {
            stmt.setInt(1, componentId);
            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    Component component = new Component();
                    component.setComponentId(rs.getInt("component_id"));
                    component.setVehicleId(rs.getString("vehicle_id"));
                    component.setType(rs.getString("type"));
                    component.setHoursUsed(rs.getDouble("hours_used"));
                    component.setWearPercentage(rs.getDouble("wear_percentage"));
                    component.setDiagnosticStatus(rs.getString("diagnostic_status"));
                    return component;
                }
            }
        }
        return null;
    }
}