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
 * DAO implementation for component operations.
 * @author Jinze Li
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
}